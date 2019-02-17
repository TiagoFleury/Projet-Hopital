package classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;
import java.util.Date;
import java.util.Locale;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

public class BaseDeDonnees {
    
    //Attributs
	public ArrayList<Chirurgie> listeChirurgies;
	public ArrayList<Chirurgien> chirurgiensExistants;
	public ArrayList<Bloc> blocsExistants;
	public TreeMap<LocalDate,Journee> listeJournees;
	public int nbConflits;
	public double tempsMoyenEntreDeuxChirurgies;
	
	//Constructeur
	public BaseDeDonnees() {
		listeChirurgies = new ArrayList<Chirurgie>();
		chirurgiensExistants = new ArrayList<Chirurgien>();
		blocsExistants = new ArrayList<Bloc>();
		listeJournees = new TreeMap<LocalDate,Journee>();
		tempsMoyenEntreDeuxChirurgies = 0;
	}
	
	// IMPORT DE BASE DE DONNEES 
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
	
	
	public void calculTempsMoyensChirurgies() {
		int somme = 0;
		int compteur = 0;
		double moyenne = 0;
		for(Chirurgien c : chirurgiensExistants) { //Pour chaque chirurgien, on va calculer son temps moyen d'operation
			for(Chirurgie chir : listeChirurgies) {
				
				if(chir.getChirurgien().equals(c)) { //Si c'est bien ce chirurgien qui a opere
					
					//Et si la chirurgie n'est pas n'est pas dans un conflit
					if(!chir.estEnConflit()) {
						//ALORS on la compte dans les stats
						compteur++;
						somme += ChronoUnit.MINUTES.between(chir.getDebut(), chir.getFin());
					}
				}
			}
			if(compteur != 0) {
				moyenne = somme/compteur;
			}
			else
				moyenne=0;
			c.setTempsMoyenChirurgie(moyenne);
			
			somme = 0;
			compteur = 0;
		}
	}
	
	
	public void calculTempsEntreDeuxChirurgies() {
		ArrayList<Chirurgie> liste = new ArrayList<>(); //Ce sera toutes les chirurgies d'un bloc, il y moyen qu'il faille faire gaffe aux jours aussi
		
		for(Bloc bloc : blocsExistants) {
			for(Chirurgie chir : listeChirurgies) {
				if(chir.getSalle().equals(bloc)) {//Si c'est une chirurgie de ce bloc
					liste.add(chir);
					
				}
			}
			
			//Ici il faut trier la liste dans l'ordre par heure de commencement
			
			for(int i=0;i<liste.size();i++) {
				//Ici faire un truc qui calcule le temps entre deux chirurgies consecutives uniquement si aucune des deux n'est dans un conflit
			}
		}
	}
	
	
	
	
	public void envoieChirurgiesChirurgien(Chirurgien albert) {
		ArrayList<Chirurgie> lesChirurgies = null;
		for (Chirurgie c : listeChirurgies) {
			if (c.getChirurgien().equals(albert)) {
				lesChirurgies.add(c);
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
			envoieChirurgiesChirurgien(c);
			for (Chirurgie chi : c.getChirurgies()) {
				leJour = chi.getDate().getDayOfWeek().toString();
				
				if (leJour.equals(joursSemaine[0])) {
					compteur1+=1; //Dimanche
				}
				if (leJour.equals(joursSemaine[1])) {
					compteur2+=1; //Lundi
				}
				if (leJour.equals(joursSemaine[2])) {
					compteur3+=1; //Mardi
				}
				if (leJour.equals(joursSemaine[3])) {
					compteur4+=1; //Mercredi
				}
				if (leJour.equals(joursSemaine[4])) {
					compteur5+=1; //Jeudi
				}
				if (leJour.equals(joursSemaine[5])) {
					compteur6+=1; // Vendredi
				}
				if (leJour.equals(joursSemaine[6])) {
					compteur7+=1; // Samedi
				}
			}
			if (c.getChirurgies().size()!=0) {
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
				
				if (leJour.equals(joursSemaine[0])) {
					compteur1+=1; //Dimanche
				}
				if (leJour.equals(joursSemaine[1])) {
					compteur2+=1; //Lundi
				}
				if (leJour.equals(joursSemaine[2])) {
					compteur3+=1; //Mardi
				}
				if (leJour.equals(joursSemaine[3])) {
					compteur4+=1; //Mercredi
				}
				if (leJour.equals(joursSemaine[4])) {
					compteur5+=1; //Jeudi
				}
				if (leJour.equals(joursSemaine[5])) {
					compteur6+=1; //Vendredi
				}
				if (leJour.equals(joursSemaine[6])) {
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
	
	
	
	
	// Methodes Statistiques générales pour un certain ensemble de donnees
	
	public double moyenneDeDonnees(ArrayList<Double> lesDonnees) {
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
	
	
	
	public double varianceDeDonnees(ArrayList<Double> lesDonnees) {
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
	
	
	
	public ArrayList<Double> intervalleConfiance95(ArrayList<Double> dodonnees) {
		ArrayList<Double> intervalle =null;
		if (dodonnees.size()>1) {
			System.out.println("il n'y a pas assez de donnees");
			return intervalle;
		}
		else {
			double moyenne=moyenneDeDonnees(dodonnees); 
			double ecartType = Math.sqrt(varianceDeDonnees(dodonnees));
			intervalle.add(moyenne - 1.96*ecartType/Math.sqrt(dodonnees.size()-1) );
			intervalle.add(moyenne + 1.96*ecartType/Math.sqrt(dodonnees.size()-1) );
			return intervalle;
		}
		
		
	}
	// MAINTENANT IL SUFFIRA DE checker une donnee au sein d'une certaine listes de donnees si elle est dans l'IC 95%
	// cela nous permettra direct d'en déduire si on la corrige ou pas etc
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] Args) {
		
		System.out.println("\n \n \n \n Et la� sur la grosse base de donnees \n");
		
		
		
		BaseDeDonnees data2 = new BaseDeDonnees();
		data2.importBase("Chirurgies_v2.csv");
		data2.organiserJournees();
		Journee jourHugo = null;
		int nbConflits = 0;
		ArrayList<Journee> journeesAconflits= new ArrayList<>();
		for(int i=0;i<data2.listeJournees.size();i++){
			jourHugo = data2.getJournee(i);

			jourHugo.detectionConflit();
			nbConflits += jourHugo.getConflits().size();
			if(jourHugo.getConflits().size()>0) {
				journeesAconflits.add(jourHugo);
			}
		}
		System.out.println("Nombre de conflits dans la base : "+nbConflits);
		System.out.println("Nombre de jours a conflits dans la base : "+journeesAconflits.size());
		
		Journee jourTest = data2.getJournee("20/01/14");
		jourTest.planningJourneeParBloc();
		jourTest.detectionConflit();
		jourTest.resoudreConflitCout0(data2, jourTest.getConflits().get(0));
		System.out.println(data2.getJournee("20/01/14").getConflits()+"\n\n");
		
		
		
		for(Journee j : journeesAconflits) {
			for(Conflit c : j.getConflits()) {
				j.resoudreConflitCout0(data2, c);
			}
		}
		
		nbConflits = 0;
		journeesAconflits= new ArrayList<>();
		for(int i=0;i<data2.listeJournees.size();i++){
			jourHugo = data2.getJournee(i);

			jourHugo.detectionConflit();
			nbConflits += jourHugo.getConflits().size();
			if(jourHugo.getConflits().size()>0) {
				journeesAconflits.add(jourHugo);
			}
		}

		System.out.println("Nombre de conflits dans la base : "+nbConflits);
		System.out.println("Nombre de jours a conflits dans la base : "+journeesAconflits.size());
		
		journeesAconflits.get(2).planningJourneeParBloc();
		
		
		//for(int i=0;i<data.listeJournees.size();i++) {
		//	data.getJournee(i).planningJourneeParBloc();
		// }
		
		
		LocalDate datest = LocalDate.of(2019, 02, 01);
		System.out.println(datest.toString());
		System.out.println(datest.getDayOfWeek().toString());
		DateFormatSymbols dfsEN = new DateFormatSymbols(Locale.ENGLISH);
		String[] joursSemaine = dfsEN.getWeekdays();
		System.out.println(joursSemaine[0].toString() + "j'ai affiché un jour, je le suppose vide");
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
	
}
