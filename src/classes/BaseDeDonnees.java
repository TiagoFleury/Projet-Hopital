package classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.text.DateFormatSymbols;
import java.util.Locale;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;
public class BaseDeDonnees {
    
    //Attributs
	public ArrayList<Chirurgie> listeChirurgies;
	public ArrayList<Chirurgien> chirurgiensExistants;
	public ArrayList<Bloc> blocsExistants;
	public TreeMap<LocalDate,Journee> listeJournees;
	public int nbConflits;
	private double[] tempsInterOperatoireBloc;
	
	//Constructeur
	public BaseDeDonnees() {
		listeChirurgies = new ArrayList<Chirurgie>();
		chirurgiensExistants = new ArrayList<Chirurgien>();
		blocsExistants = new ArrayList<Bloc>();
		listeJournees = new TreeMap<LocalDate,Journee>();
		tempsInterOperatoireBloc = new double[3];
	}
	
	// ORGANISATION DES DONNEES
	//Methode pour remplir la liste des journees.
	public void organiserJournees() {
		for(Chirurgie c : listeChirurgies) {
			//Cas ou il y a deja une Journee cree sur cette date
			if(listeJournees.containsKey(c.getDate())) {
				Journee j = listeJournees.get(c.getDate()); //Recuperation du jour
				j.importerInfoChirurgie(c); //Ajout de la chirurgies et des infos dans ce jour
			}
			//Cas ou la journee n'a pas encore ete traitee
			else {
				//Ajout nouvelle journee avec comme cle la nouvelle date
				listeJournees.put(c.getDate(), new Journee(c));
				
			}
		}
		for(int i=0;i<listeJournees.size();i++) { //On initialise aussi tous les conflits
			getJournee(i).detectionConflit();
		}
		
	}
	
	
	//utilitaires pour importBase
	private Bloc blocExisteDeja(String nom) {
			for(Bloc b : blocsExistants) {
				if(b.getName().equals(nom)) {
					return b;
				}
			}
		return null;
	}
	private Chirurgien chirurgienExisteDeja(String nom) {
		for(Chirurgien c : chirurgiensExistants) {
			if(c.getName().equals(nom)) {
				return c;
			}
		}
		return null;
	}
	
	
	//EXPORT EN CSV
	
	public void exportBase() {
		File fichier = new File("NouvelleBase.csv");
	
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(fichier));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			writer.write("ID CHIRURGIE;DATE CHIRURGIE;HEURE_DEBUT CHIRURGIE;HEURE_FIN CHIRURGIE;SALLE;CHIRURGIEN");
			writer.newLine();
			Collections.sort(listeChirurgies,Chirurgie.CHRONOLOGIQUE);
			for(Chirurgie chir : listeChirurgies) {
					writer.write(""+chir.getID()+";"+chir.getDate().format(DateTimeFormatter.ofPattern("DD/MM/YYYY"))+";"+chir.getDebut()+";"+chir.getFin()+";"+chir.getSalle().getName()+";"+chir.getChirurgien().getName());
					writer.newLine();
					writer.flush();
			}
			writer.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	//IMPORT
	public void importBase(String cheminFichier) {
		listeChirurgies = new ArrayList<Chirurgie>();
		
		File fichier = new File(cheminFichier);
		if(!fichier.exists()) {
			System.out.println("Le fichier "+cheminFichier+"n'existe pas");
			return;
		}
		String champs[] = new String[6]; //Ca va stocker les champs
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fichier));
		}catch(FileNotFoundException e) {
			System.out.println("Erreur : fichier non trouve");
			System.out.println(e);
		}
		
		try {
			reader.readLine();//On passe la premiere ligne
			String ligne = reader.readLine();
			
			
			while(ligne!=null) {
					
					champs = ligne.split(";");
					Chirurgie ch = new Chirurgie(champs);
					listeChirurgies.add(ch);
					Bloc b=null;
					if((b=blocExisteDeja(champs[4])) != null) { //Verifie si un bloc avec ce nom existe deja
						//Si oui on pointe juste dessus
						ch.setSalle(b);
					}
					else { //Sinon on en cree un nouveau
						b = new Bloc(champs[4]);
						ch.setSalle(b);
						blocsExistants.add(b); //On l'ajoute a la liste des blocs existants
					}
					
					//On fait pareil pour les chirurgiens.
					
					Chirurgien surgeon;
					if((surgeon=chirurgienExisteDeja(champs[5])) != null) { //Verifie si un chirurgiens avec ce nom existe deja
						//Si oui on pointe juste dessus
						ch.setChirurgien(surgeon);
					}
					else { //Sinon on en cree un nouveau
						surgeon = new Chirurgien(champs[5]);
						ch.setChirurgien(surgeon);
						chirurgiensExistants.add(surgeon); //On l'ajoute a la liste des chirurgiens existants
					}
					ligne = reader.readLine();
					
			}
		}catch(IOException e) {
			System.out.println(e);
		}
		
	}
	
	
	
	

	
	
	//STATS
	
	
	public void calculTempsMoyensChirurgien() {
		int somme = 0;
		int compteur = 0;
		double moyenne = 0;
		ArrayList<Double> donnees= new ArrayList<>();
		for(Chirurgien c : chirurgiensExistants) { //Pour chaque chirurgien, on va calculer son temps moyen d'operation
			for(Chirurgie chir : listeChirurgies) {
				
				if(chir.getChirurgien().equals(c)) { //Si c'est bien ce chirurgien qui a opere
					
					//Et si la chirurgie n'est pas n'est pas dans un conflit
					if(!chir.estEnConflit()) {
						//ALORS on la compte dans les stats
						compteur++;
						somme += chir.getDuree();
						donnees.add(chir.getDuree());
					}
				}
			}
			if(compteur != 0) {
				moyenne = (int) (somme/compteur);
			}
			else
				moyenne=0;
			c.setTempsMoyenChirurgie(moyenne);
			System.out.println("TAILLE DU TABLEAU POUR "+c+": "+ donnees.size());
			c.setICtempsMoyen(intervalleConfiance95(donnees));
			somme = 0;
			compteur = 0;
			donnees.clear();
			
		}
	}
	
	//Attention pour cette methode il faut que les attributs "estEnConflit" soient a jour
	public void calculTempsEntreDeuxChirurgiesMemeBloc() {
		ArrayList<Chirurgie> liste = new ArrayList<>(); //Ce sera toutes les chirurgies d'un bloc, il y moyen qu'il faille faire gaffe aux jours aussi
		double somme = 0;
		int compte = 0;
		ArrayList<Double> donnees = new ArrayList<>(); //Stocke les donnees pour l'intervalle de confiance
		
		
		for(int i=0;i<listeJournees.size();i++) {
			Journee journee = getJournee(i);
			
			for(Bloc bloc : journee.getBlocs()) { //Pour chaque blocs de chaque journees
				for(Chirurgie chir : journee.getChirurgiesJour()) { //On va recuperer toutes les chirurgies de ce bloc
					
					if(chir.getSalle().equals(bloc)) {//Si c'est une chirurgie de ce bloc
						liste.add(chir); //On l'ajoute a la liste
						
					}
				}
				
				
				Collections.sort(liste,Chirurgie.CHRONOLOGIQUE);
				
				
				for(int j=0;j<liste.size()-1;j++) {
					if(!(liste.get(j).estEnConflit()) && !(liste.get(j+1).estEnConflit())) { //Si aucune des deux chirurgies n'est en conflit,
						//On compte leur ecart
						compte++;
						long ecart = ChronoUnit.MINUTES.between(liste.get(j).getFin(), liste.get(j+1).getDebut());
						somme += ecart;
						donnees.add((double)ecart);
						//System.out.println("+"+ecart+"min de comptees pour ch"+liste.get(j).getID()+" et ch"+liste.get(j+1).getID());
					}
					
				}
				liste.clear();
			}
		}
		double moyenne = somme / compte;
		setTempsMoyenInteroperatoireBloc((int)moyenne);
		setICtempsInteroperatoireBloc(intervalleConfiance95(donnees));
	}
	


	public void calculTempsEntreDeuxChirurgiesMemeChirurgien() {
		ArrayList<Chirurgie> liste = new ArrayList<>(); //Ce sera toutes les chirurgies d'un chirurgien dans une journee
		double somme = 0;
		int compte = 0;
		ArrayList<Double> donnees = new ArrayList<>(); //Stocke les donnees pour l'intervalle de confiance
		
		for(Chirurgien chirurgien : chirurgiensExistants) { //Pour chaque chirurgien, on va calculer la moyenne
			
			for(int i=0;i<listeJournees.size();i++) { //Pour chaque journee
				Journee journee = getJournee(i);
			
				for(Chirurgie chir : journee.getChirurgiesJour()) { //On va recuperer toutes les chirurgies de ce chirurgien
					
					if(chir.getChirurgien().equals(chirurgien)) {//On verifie que c'est une chirurgie de ce chirurgien
						liste.add(chir); //On l'ajoute a la liste
						
					}
				}
				
				//Ensuite on trie par ordre chronologique d'heure de debut
				Collections.sort(liste,Chirurgie.CHRONOLOGIQUE);
				
				
				for(int j=0;j<liste.size()-1;j++) {
					if(!(liste.get(j).estEnConflit()) && !(liste.get(j+1).estEnConflit())) { //Si aucune des deux chirurgies n'est en conflit,
						//On compte leur ecart
						compte++;
						long ecart = ChronoUnit.MINUTES.between(liste.get(j).getFin(), liste.get(j+1).getDebut());
						somme += ecart;
						donnees.add((double) ecart);
						//System.out.println("+"+ecart+"min de comptees pour ch"+liste.get(j).getID()+" et ch"+liste.get(j+1).getID());
					}
					
				}
				liste.clear();
			}
			double moyenne = somme / compte;
			chirurgien.setTempsInteroperatoireMoyen((int) moyenne);
			chirurgien.setICtempsInteroperatoire(intervalleConfiance95(donnees));
			donnees.clear();
			somme=0;
			compte=0;
			
		}
	}
	
	
	public void calculTempsDeTravailChirurgiens() {
		
		ArrayList<Chirurgie> liste = new ArrayList<>(); //Ce sera toutes les chirurgies d'un chirurgien dans une journee
		double somme = 0;
		long compte = 0;
		ArrayList<Double> donnees = new ArrayList<>(); //Stocke les donnees pour l'intervalle de confiance
		
		for(Chirurgien chirurgien : chirurgiensExistants) { //Pour chaque chirurgien, on va calculer la temps moyen de travail par jour
			
			for(int i=0;i<listeJournees.size();i++) { //Pour chaque journee
				Journee journee = getJournee(i);
				int tempsJournee = 0; //On va calculer le temps de travail sur la journee
				
				for(Chirurgie chir : journee.getChirurgiesJour()) { //On va recuperer toutes les chirurgies de ce chirurgien
					
					if(chir.getChirurgien().equals(chirurgien) && !chir.estEnConflit()) {//On verifie que c'est une chirurgie de ce chirurgien et qu'elle n'est pas en conflit
						liste.add(chir); //On l'ajoute a la liste
						
					}
				}
				
				if(liste.size()!=0) { //Si c'est une journee ou il travaille, c'est a dire au moins une chirurgie de lui sans conflit
					for(Chirurgie c : liste) {
						tempsJournee+=c.getDuree();//On somme toutes les durees
						
					}
					donnees.add((double)tempsJournee);
					somme+=tempsJournee;
					compte++;
				}
				liste.clear();
			}
			double moyenne = somme / compte;
			chirurgien.setTempsTravailMoyenParJour((int)moyenne);
			chirurgien.setICtempsTravailParJour(intervalleConfiance95(donnees));
			donnees.clear();
			somme=0;
			compte=0;
			
		}
	}
	
	
	
	public void envoieChirurgiesEtLesTempsChirurgien(Chirurgien albert) {
		ArrayList<Chirurgie> lesChirurgies = null;
		ArrayList<Double> lesTemps=null;
		for (Chirurgie c : listeChirurgies) {
			if (c.getChirurgien().equals(albert)) {
				lesChirurgies.add(c);
				lesTemps.add(c.getDuree());
			}
		}
		albert.setChirurgies(lesChirurgies);
	}
	
	
	
	public void calculJoursRecurrentsDeTravailChirurgiens() {
		DateFormatSymbols dfsEN = new DateFormatSymbols(Locale.ENGLISH);
		String[] joursSemaine = dfsEN.getWeekdays(); // Je creee un [jour de la semaine 1=Sunday, 7=Saturday], pour les comparer avec les dates
		
		ArrayList<Double> listeProportionsJours = null ;
		int compteur1 = 0, compteur2=0, compteur3=0, compteur4=0, compteur5=0, compteur6=0, compteur7=0;
		String leJour = null ; 
		for (Chirurgien c : chirurgiensExistants) {
			listeProportionsJours = null;
			compteur1 = 0 ;  compteur2=0 ; compteur3=0 ; compteur4=0 ; compteur5=0 ; compteur6=0; compteur7 = 0;
			envoieChirurgiesEtLesTempsChirurgien(c);
			for (Chirurgie chi : c.getChirurgies()) {
				leJour = chi.getDate().getDayOfWeek().toString();
				
				if (leJour.equals(joursSemaine[1].toString().toUpperCase())) {
					compteur1+=1; //Dimanche
				}
				if (leJour.equals(joursSemaine[2].toString().toUpperCase())) {
					compteur2+=1; //Lundi
				}
				if (leJour.equals(joursSemaine[3].toString().toUpperCase())) {
					compteur3+=1; //Mardi
				}
				if (leJour.equals(joursSemaine[4].toString().toUpperCase())) {
					compteur4+=1; //Mercredi
				}
				if (leJour.equals(joursSemaine[5].toString().toUpperCase())) {
					compteur5+=1; //Jeudi
				}
				if (leJour.equals(joursSemaine[6].toString().toUpperCase())) {
					compteur6+=1; // Vendredi
				}
				if (leJour.equals(joursSemaine[7].toString().toUpperCase())) {
					compteur7+=1; // Samedi
				}
			}
			if (c.getChirurgies().size()!=0) { // ATTENTION : je crée ici une liste de taille 7 / donc position de 0 à 6
				listeProportionsJours.add((double) compteur1/c.getChirurgies().size());
				listeProportionsJours.add((double) compteur2/c.getChirurgies().size());
				listeProportionsJours.add((double) compteur3/c.getChirurgies().size());
				listeProportionsJours.add((double) compteur4/c.getChirurgies().size());
				listeProportionsJours.add((double) compteur5/c.getChirurgies().size());
				listeProportionsJours.add((double) compteur6/c.getChirurgies().size());
				listeProportionsJours.add((double) compteur7/c.getChirurgies().size());
			}
			else {
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
			}
			c.setMoyenneJours(listeProportionsJours);
		}
	}
	
	
	
	public void envoieChirurgiesBloc(Bloc cantine) {
		ArrayList<Chirurgie> lesChirurgies=null;
		for (Chirurgie c : listeChirurgies) {
			if (c.getSalle().equals(cantine)) {
				lesChirurgies.add(c);
			}
		}
		cantine.setChirurgies(lesChirurgies);
		
	}
	
	public void calculJoursRecurrentsDeTravailBlocs() {
		DateFormatSymbols dfsEN = new DateFormatSymbols(Locale.ENGLISH);
		String[] joursSemaine = dfsEN.getWeekdays(); // Je creee un [jour de la semaine 1=Sunday, 7=Saturday], pour les comparer avec les dates
		
		ArrayList<Double> listeProportionsJours = null ;
		int compteur1 = 0, compteur2=0, compteur3=0, compteur4=0, compteur5=0, compteur6=0, compteur7=0;
		String leJour = null ; 
		for (Bloc salle : blocsExistants) {
			listeProportionsJours = null;
			compteur1 = 0 ;  compteur2=0 ; compteur3=0 ; compteur4=0 ; compteur5=0 ; compteur6=0; compteur7 = 0;
			envoieChirurgiesBloc(salle);
			for (Chirurgie chi : salle.getChirurgies()) {
				leJour = chi.getDate().getDayOfWeek().toString();
				
				if (leJour.equals(joursSemaine[1].toString().toUpperCase())) {
					compteur1+=1; //Dimanche
				}
				if (leJour.equals(joursSemaine[2].toString().toUpperCase())) {
					compteur2+=1; //Lundi
				}
				if (leJour.equals(joursSemaine[3].toString().toUpperCase())) {
					compteur3+=1; //Mardi
				}
				if (leJour.equals(joursSemaine[4].toString().toUpperCase())) {
					compteur4+=1; //Mercredi
				}
				if (leJour.equals(joursSemaine[5].toString().toUpperCase())) {
					compteur5+=1; //Jeudi
				}
				if (leJour.equals(joursSemaine[6].toString().toUpperCase())) {
					compteur6+=1; //Vendredi
				}
				if (leJour.equals(joursSemaine[7].toString().toUpperCase())) {
					compteur7+=1; //Samedi
				}
			}
			if (salle.getChirurgies().size()!=0) {
				listeProportionsJours.add((double) compteur1/salle.getChirurgies().size());
				listeProportionsJours.add((double) compteur2/salle.getChirurgies().size());
				listeProportionsJours.add((double) compteur3/salle.getChirurgies().size());
				listeProportionsJours.add((double) compteur4/salle.getChirurgies().size());
				listeProportionsJours.add((double) compteur5/salle.getChirurgies().size());
				listeProportionsJours.add((double) compteur6/salle.getChirurgies().size());
				listeProportionsJours.add((double) compteur7/salle.getChirurgies().size());
			}
			else {
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
				listeProportionsJours.add((double) 0);
			}
			salle.setMoyennesJours(listeProportionsJours);
		}
	}
	

	
	
	// Methodes Statistiques generales pour un certain ensemble de donnees
	
	public static double moyenneDeDonnees(ArrayList<Double> lesDonnees) {
		int somme = 0;
		double moyenne = 0;
		for (Double d : lesDonnees) {
			somme+=d;
		}
		if (lesDonnees.size()!=0) {
			return somme/lesDonnees.size();
		}
		else { return 0;}
	}
	
	public static double varianceDeDonnees(ArrayList<Double> lesDonnees) {
		double moy = moyenneDeDonnees(lesDonnees);
		double sum = 0;
		for (double d : lesDonnees) {
			sum=sum+ ((d-moy)*(d-moy));
		}
		if (lesDonnees.size()>1) {
			return sum/(lesDonnees.size()-1);
		}
		else { return 0;}
	}
	
	
	
	public static ArrayList<Double> intervalleConfiance95(ArrayList<Double> dodonnees) {
		ArrayList<Double> intervalle = new ArrayList<>();
		if (dodonnees.size()<1) {
			System.out.println("il n'y a pas assez de donnees");
			intervalle.add(0.0);
			intervalle.add(0.0);
			return intervalle;
		}
		else {
			double moyenne=moyenneDeDonnees(dodonnees); 
			double ecartType = Math.sqrt(varianceDeDonnees(dodonnees));
			intervalle.add((int)(moyenne - 1.96*ecartType/Math.sqrt(dodonnees.size()-1))/1.0);
			intervalle.add((int)(moyenne + 1.96*ecartType/Math.sqrt(dodonnees.size()-1))/1.0);
			return intervalle;
		}
		
	}
	// MAINTENANT IL SUFFIRA DE checker une donnee au sein d'une certaine listes de donnees si elle est dans l'IC 95%
	// cela nous permettra direct d'en deduire si on la corrige ou pas etc
	

	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] Args) {
		
		
		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("Chirurgies_v2.csv");
		data.organiserJournees();
		int nbInterferences = 0;
		int nbConflits=0;
		int nbUbiquites=0;
		int nbChevauchements=0;
		
		for(int i=0;i<data.listeJournees.size();i++) {
			Journee j = data.getJournee(i);
			
			for(Conflit c : j.getConflits()) {
				if(c instanceof Interference) {
					//Interference interf = (Interference) c;
					j.planningJourneeParBloc();
					nbInterferences++;
				}
				if(c instanceof Ubiquite) {
					nbUbiquites++;
				}
				if(c instanceof Chevauchement) {
					nbChevauchements++;
		System.out.println("Nombre d'interferences : "+nbInterferences+"\nNombre Conflits : "+nbConflits+"\nNombre Ubiquites : "+nbUbiquites+"\nNombre Chevauchements : "+nbChevauchements);
				}
			}
		}
		
	}
		
	
	
	
	
	
	
	
	
	
	
	
	//ACCESSEURS
	public Journee getJournee(int indice) { //Retourne la n eme journee de listeJournee
		
    	Set<LocalDate> cles = listeJournees.keySet();
    	if(indice>=cles.size()) {
    		System.out.println("La journee demandee n'existe pas (index out of range)");
    		return null;
    	}
		return listeJournees.get(cles.toArray()[indice]);

    }
	
	public Journee getJournee(String date) { //Format jj/mm/aa
		if(date.length() !=8) {
			System.out.println("Mauvais format de date");
			return null;
		}
		
		String[] champs = date.split("/");
		LocalDate dateVoulue = LocalDate.of(Integer.parseInt(champs[2])+2000, Integer.parseInt(champs[1]), Integer.parseInt(champs[0]));
		
		if(!listeJournees.containsKey(dateVoulue)) {
			//Si la date voulue n'existe pas
			System.out.println("La journee "+date+" n'existe pas dans cette base de donnees");
			return null;
		}
		else {
			return listeJournees.get(dateVoulue);
		}
		
	}
	
	public ArrayList<Chirurgien> getTousChirurgiens(){
		return this.chirurgiensExistants;
	}
	
	public ArrayList<Bloc> getTousBlocs(){
		return this.blocsExistants;
	}
	
	public ArrayList<Chirurgie> getTousChirurgies(){
		return this.listeChirurgies;
	}

	public double getTempsMoyenInteroperatoireBloc() {
		return tempsInterOperatoireBloc[0];
	}

	
	
	//MUTATEURS
	public void setTempsMoyenInteroperatoireBloc(double tempsMoyenEntreDeuxChirurgiesMemeBloc) {
		this.tempsInterOperatoireBloc[0] = tempsMoyenEntreDeuxChirurgiesMemeBloc;
	}
	
	private void setICtempsInteroperatoireBloc(ArrayList<Double> IC95) {
		tempsInterOperatoireBloc[1] = IC95.get(0);
		tempsInterOperatoireBloc[2] = IC95.get(1);
		
	}
	
}
