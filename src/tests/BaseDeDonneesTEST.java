package tests;

import classes.BaseDeDonnees;
import classes.Bloc;
import classes.Chirurgien;
import classes.Journee;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

class BaseDeDonneesTEST {

	
	@Test
	void testCalculTempsMoyensChirurgie() {
		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("MiniBase.csv");
		
		data.organiserJournees();
		
		data.calculTempsMoyensChirurgies();
		
		System.out.println("------------------------");
		for(int i=0;i<data.listeJournees.size();i++) {
			data.getJournee(i).planningJourneeParChirurgien();
		}
		//Test pour le temps moyen de gregory house (3 chirurgies comptables de 120 + 90 + 120)
		// 330/3 = 110
		
		assertEquals(data.getTousChirurgiens().get(0).getTempsMoyen(), 110);
		
	}
	
	
	@Test
	void testOrganiserJournees() {
		//Grosse base
		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("Chirurgies_v2.csv");
		
		data.organiserJournees();
		Journee jour = data.getJournee("31/12/16");
		
		jour.planningJourneeParBloc();
		
		
		//Petite base
		BaseDeDonnees data2 = new BaseDeDonnees();
		data2.importBase("MiniBase.csv");
		
		data2.organiserJournees();
		Journee jour2 = data2.getJournee(1);
		
		assertEquals(6,data2.listeJournees.size());
		
		jour2.planningJourneeParChirurgien();
		
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
		BaseDeDonnees data2 = new BaseDeDonnees();
		data2.importBase("Chirurgies_v2.csv");
		System.out.println("Les blocs : "+data2.getTousBlocs());
	}
	
	
	
	@Test
	void testGetJournee() {
		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("MiniBase.csv");
		data.organiserJournees();
		Journee j = data.getJournee(6);
		assertTrue(j == null);
		j = data.getJournee(5);
		
		assertEquals(j.getDate().toString(),"2019-01-06");
		
		j = data.getJournee("01/01/19");
	
		assertEquals(j.getDate().toString(),"2019-01-01");
		
		j = data.getJournee("06/01/19");
		assertEquals(j.getDate().toString(),"2019-01-06");
		

		j = data.getJournee("07/01/19");
		assertTrue(j == null);
		
	}
}
