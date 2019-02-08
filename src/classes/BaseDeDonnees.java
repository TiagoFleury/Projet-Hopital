package classes;

import java.time.LocalDate;
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
	
	public static void main(String[] Args) {
		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("MiniBase.csv");
		for(Chirurgie c : data.listeChirurgies) {
			System.out.println(c);
		}
		System.out.println("\nListe chirurgiens : ");
		
		for(Chirurgien c : data.chirurgiensExistants) {
			System.out.println("- "+c);
		}
		
		System.out.println("\n\nListe des blocs :");
		for(Bloc b : data.blocsExistants) {
			System.out.println(b);
		}
		
		
		// Je tente une résolution cout0 
		System.out.println("\n \n \n Hugo -- Test de resolution a cout 0 -- 1er essai \n \n");
		BaseDeDonnees data2 = new BaseDeDonnees();
		data2.importBase("Chirurgies_v2.csv");
		data.organiserJournees();
		Journee jourHugo = data.getJournee("01/01/19");
		System.out.println("Nb de chirurgiens mobilises : "+jourHugo.getChirurgienMobilises().size());
		
		jourHugo.detectionConflit();
		jourHugo.planningJourneeParBloc();
		System.out.println(jourHugo.getConflits().toString());
		
		if (jourHugo.getConflits().size()!=0) {
			jourHugo.resoudreConflitCout0(data, jourHugo.getConflits().get(0));
		}
		
		jourHugo.detectionConflit();
		jourHugo.planningJourneeParBloc();
		System.out.println(jourHugo.getConflits().toString());
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
	
}
