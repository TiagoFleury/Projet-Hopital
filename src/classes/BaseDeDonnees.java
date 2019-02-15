package classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BaseDeDonnees {
    
    //Attributs
	public ArrayList<Chirurgie> listeChirurgies;
	public ArrayList<Chirurgien> chirurgiensExistants;
	public ArrayList<Bloc> blocsExistants;
	public TreeMap<LocalDate,Journee> listeJournees;
	public int nbConflits;
	//Constructeur
	public BaseDeDonnees() {
		listeChirurgies = new ArrayList<Chirurgie>();
		chirurgiensExistants = new ArrayList<Chirurgien>();
		blocsExistants = new ArrayList<Bloc>();
		listeJournees = new TreeMap<LocalDate,Journee>();
	}
	
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
	
	
	
	public float tempsMoyenChirurgien(Chirurgien albert) {
		int sum = 0, compteur = 0;
		ArrayList<Long> tempsChirurgies = new ArrayList<Long>();
		for (Chirurgie c : this.listeChirurgies) {
			if (c.getChirurgien().equals(albert)){
				tempsChirurgies.add(ChronoUnit.MINUTES.between(c.getDebut(), c.getFin()));
				sum+=(ChronoUnit.MINUTES.between(c.getDebut(), c.getFin()));
				compteur+=1;
			}
		}
		System.out.println("Ses temps de chirurgies sont (pour repérer les abérations) : \n " + tempsChirurgies.toString());
		return sum/compteur;
	}
	

	
	public void envoieChirurgies(Chirurgien albert) {
		for (Chirurgie c : listeChirurgies) {
			if (c.getChirurgien().equals(albert)) {
				albert.getChirurgies().add(c);
			}
		}
	}
	
	
	
	
	
	public static void main(String[] Args) {
		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("MiniBase.csv");
		data.organiserJournees();
		Journee jour1 = data.getJournee("04/01/19");
		
		
		
		// Je tente 2 resolutions cout0 
		System.out.println("\n \n \n Hugo -- Test de resolution a cout 0 -- 1er essai \n \n");
		System.out.println("\n \n \n  Ici sur la Mini Base \n");
		jour1.planningJourneeParBloc();
		jour1.detectionConflit();
		System.out.println("\n Voici la liste des conflits encore presents : " + jour1.getConflits().toString());
		
		if (jour1.getConflits().size()!=0) {
			jour1.resoudreConflitCout0(data, jour1.getConflits().get(0));
			jour1.detectionConflit();
			jour1.planningJourneeParBloc();
			System.out.println(jour1.getConflits().toString());
		}
		
		
		
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
		
		data2.getJournee("21/01/14").planningJourneeParBloc();
		data2.getJournee("21/01/14").detectionConflit();
		System.out.println(data2.getJournee("21/01/14").getConflits()+"\n\n");
		
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
		
		journeesAconflits.get(1).planningJourneeParBloc();
		
		System.out.println("\n \n \n \n ALALLALALA \n");
		Journee jourHugoBIS = data2.getJournee("21/01/14");
		jourHugoBIS.planningJourneeParBloc();
		jourHugoBIS.detectionConflit();
		System.out.println("\n Voici la liste des conflits encore présents : " + jourHugoBIS.getConflits().toString());
		
		
		
		
		
		//for(int i=0;i<data.listeJournees.size();i++) {
		//	data.getJournee(i).planningJourneeParBloc();
		// }
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
