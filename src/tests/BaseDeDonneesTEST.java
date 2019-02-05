package tests;

import classes.BaseDeDonnees;
import classes.Bloc;
import classes.Chirurgien;
import classes.Journee;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.Test;

class BaseDeDonneesTEST {

	@Test
	void testOrganiserJournees() {
		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("MiniBase.csv");
		
		data.organiserJournees();
		Set<LocalDate> cles = data.listeJournees.keySet();
		
//		for(LocalDate d : cles) {
//			data.listeJournees.get(d).planningJourneeParChirurgien();
//		}
		
	}
	
	@Test
	void testImportBase() {
		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("MiniBase.csv");
		String resultat = "";
		
		resultat = "" + data.listeChirurgies.get(0);
		assertEquals(resultat, "chir1 E1 - GREGORY HOUSE  [08:00 -- 11:00] -- 2019-01-01");
		resultat = "" + data.listeChirurgies.get(24);
		assertEquals(resultat, "chir25 E3 - REMY HADLEY  [12:30 -- 15:00] -- 2019-01-06");
		
		resultat = "";
		for(Chirurgien c : data.chirurgiensExistants) {
			resultat+= c+",";
		}
		assertEquals(resultat, "GREGORY HOUSE,ROBERT CHASE,LAWRENCE KUTNER,MIRANDA BAILEY,JAMES WILSON,LISA CUDDY,CHRIS TAUB,ERIC FOREMAN,REMY HADLEY,");
		
		
		//Test des blocs
		resultat = "";
		for(Bloc b : data.blocsExistants) {
			resultat += b+",";
		}
		assertEquals(resultat, "BLOC-E1  id:1,BLOC-E2  id:2,BLOC-E3  id:3,");
	}

}
