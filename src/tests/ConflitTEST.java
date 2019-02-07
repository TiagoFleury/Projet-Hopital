package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import classes.BaseDeDonnees;
import classes.Bloc;
import classes.Chirurgie;
import classes.Chirurgien;
import classes.Journee;
import classes.Bloc;


import org.junit.jupiter.api.Test;


class ConflitTEST {

	@Test
	void testConflit() {
		fail("Not yet implemented"); // TODO
	}

	// Pour chaque sorte de conflit faire 3 conflits différents, dont 2 sont égaux
	@Test
	void testEqualsObject() {
		BaseDeDonnees data = new BaseDeDonnees();
		data.importBase("Chirurgies_v2.csv");
		
		data.organiserJournees();
		Set<LocalDate> cles = data.listeJournees.keySet();
		Journee jour = data.listeJournees.get(cles.toArray()[0]);
		
		
		
		
		
		
		LocalDate date1 = LocalDate.of(2019, 1, 1);
		
		LocalTime time1Deb = LocalTime.of(8, 0);
		LocalTime time1Fin = LocalTime.of(11, 0);
		
		LocalTime time2Deb = LocalTime.of(10, 0);
		LocalTime time2Fin = LocalTime.of(13, 0);
		
		LocalTime time3Deb = LocalTime.of(14, 0);
		LocalTime time3Fin = LocalTime.of(16, 0);
		
		Chirurgien albert = new Chirurgien("Albert");
		Chirurgien marius = new Chirurgien("Marius");
		
		Bloc gymnase = new Bloc("BLOC-E1");
		Bloc cantine = new Bloc("BLOC-E2");
		
		
		// 1. Ubiquité 
		
		Chirurgie ch1 = new Chirurgie(date1, time1Deb, time1Fin, gymnase, albert);
		Chirurgie ch2 = new Chirurgie();
		Chirurgie ch3 = new Chirurgie();
		Conflit c1 = new Conflit(ch1, ch2);
	}

	// Tester également dans la classe JourneeTEST la méthode conflitOuPas()
}
