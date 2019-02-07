package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

import classes.BaseDeDonnees;
import classes.Chirurgie;
import classes.Journee;

class JourneeTEST {
	
//	@Test 
//	void testTriParBloc() {
//		BaseDeDonnees data = new BaseDeDonnees();
//		data.importBase("MiniBase.csv");
//		
//		String str1 = Chirurgie.afficherListe(data.listeChirurgies);
//		System.out.println(str1);
//		
//		System.out.println(str1+"\n");
//		
//		Journee.triParBlocs(data.listeChirurgies);
//		for(Chirurgie c : data.listeChirurgies)
//			System.out.println(c);
//		
//		
//		
//		
//	}
	
	@Test
	void testTriParChirurgien() {
		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("MiniBase.csv");
		Journee.triParChirurgien(data.listeChirurgies);
		for(Chirurgie c : data.listeChirurgies)
			System.out.println(c);
	}

}
