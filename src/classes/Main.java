package classes;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("Chirurgies_v2.csv");
		data.organiserJournees();
		
		data.calculTempsMoyensChirurgien();
		data.calculTempsDeTravailChirurgiens();
		data.calculTempsEntreDeuxChirurgiesMemeBloc();
		data.calculTempsEntreDeuxChirurgiesMemeChirurgien();
		data.anomaliesSurchargeChirurgiens();
		data.calculJoursRecurrentsDeTravailChirurgiens();
		data.calculPlagesHorairesHabituellesChirurgiens();
		
		File fichier = new File("logs.txt");
		
		BufferedWriter writer = null;
		
		Scanner scan = new Scanner(System.in);
		System.out.println("Choisissez le degre de resolution : (1, 2, 3) ");
		int degre = scan.nextInt();
		
		try {
			writer = new BufferedWriter(new FileWriter(fichier));
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		
		int[] resultats = new int[3];
		resultats[0] = 0;
		resultats[1] = 0;
		resultats[2] = 0;
		for (int i = 0 ; i <data.listeJournees.size() ; i++){
			Journee j = data.getJournee(i);
			int[] resultatsJournee = j.resoudreConflits(writer, data, degre);
			
	    	resultats[0]+=resultatsJournee[0];
	    	resultats[1]+=resultatsJournee[1];
	    	resultats[2]+=resultatsJournee[2];
		}
		try {
			writer.newLine();
			writer.newLine();
	    	writer.newLine();
	    	writer.write("Resolutions faites :   "+resultats[0]+" evidentes    "+resultats[1]+" moyennes    "+resultats[2]+" peu coherentes");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("---------------------------------------------------------------------------------------------------------------------");

    	System.out.println("---------------------------------------------------------------------------------------------------------------------");
    	System.out.println("\n\n\nResolutions faites :   "+resultats[0]+" evidentes    "+resultats[1]+" moyennes    "+resultats[2]+" peu coherentes");
		
    	int compteInterf=0;
    	int compteUbiq=0;
    	int compteChevauch=0;
    	for(int i=0;i<data.listeJournees.size();i++) {//On regarde combien il reste de conflits
    		Journee j = data.getJournee(i);
    		j.detectionConflit();
    		for(Conflit c : j.getConflits()) {
    			if(c instanceof Interference)
    				compteInterf++;
    			if(c instanceof Ubiquite)
    				compteUbiq++;
    			if(c instanceof Chevauchement)
    				compteChevauch++;
    		}
    	}
    	System.out.println("\nIl reste "+compteInterf+"/25 interferences.\n"+compteUbiq+"/247 ubiquites.\n"+compteChevauch+"/72 chevauchements   \n\n=>  "+(344-compteInterf-compteUbiq-compteChevauch)+"/344 conflits resolus");
		
    	
		try {
			writer.newLine();
			writer.write("Il reste "+compteInterf+"/25 interferences, "+compteUbiq+"/247 ubiquites "+compteChevauch+"/72 chevauchements   =>  "+(344-compteInterf-compteUbiq-compteChevauch)+"/344 conflits resolus");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		data.exportBase();
	}

}
