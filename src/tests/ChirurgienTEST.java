package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import classes.Chirurgien;

class ChirurgienTEST {

	@Test
	void testEquals() {
		Chirurgien c = new Chirurgien("LAWRENCE KUTNER");
		Chirurgien c2 = new Chirurgien("LAWRENCE KUTNER");
		Chirurgien c3 = new Chirurgien("ROBERT CHASE");
		assertTrue(c.equals(c2));
		assertTrue(!c.equals(c3));
	}

}
