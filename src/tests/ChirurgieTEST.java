package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import classes.Bloc;
import classes.Chirurgie;
import classes.Chirurgien;

class ChirurgieTEST {

	@Test
	void equalsTEST() {
		
		String texte1 = "6;02/01/2019;08:00:00;11:00:00;BLOC-E1;LAWRENCE KUTNER";
		String[] champs1 = texte1.split(";");
		
		//Finis 10 minutes plus tard que le 1
		String texte2 = "7;02/01/2019;08:00:00;11:10:00;BLOC-E1;LAWRENCE KUTNER";
		String[] champs2 = texte2.split(";");
		
		//Meme texte que le 1
		String texte3 = "6;02/01/2019;08:00:00;11:00:00;BLOC-E1;LAWRENCE KUTNER";
		String[] champs3 = texte3.split(";");
		
		Chirurgie c1 = new Chirurgie(champs1);
		Chirurgie c2 = new Chirurgie(champs2);
		Chirurgie c3 = new Chirurgie(champs3);
		//Dans les constructeurs ca met pas le chirurgien ni le bloc.
		
		c1.setChirurgien(new Chirurgien(champs1[5]));
		c2.setChirurgien(new Chirurgien(champs2[5]));
		c3.setChirurgien(new Chirurgien(champs3[5]));
		
		c1.setSalle(new Bloc(champs1[4]));
		c2.setSalle(new Bloc(champs2[4]));
		c3.setSalle(new Bloc(champs3[4]));
		
		assertTrue(c1.getChirurgien().equals(c3.getChirurgien()));
		assertTrue(c1.getSalle().equals(c3.getSalle()));
		
		
		assertTrue(c1.equals(c3));
		assertTrue(c3.equals(c1));
		assertTrue(!c2.equals(c1));
	}

}
