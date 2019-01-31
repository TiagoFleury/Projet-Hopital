package tests;
import classes.Bloc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BlocTEST {

	@Test
	void testBloc() {
		Bloc jetesteceBloc = new Bloc("BLOC-E1");
		assertEquals(1, jetesteceBloc.getID());
		
		Bloc test2 = new Bloc("BLOC-E34");
		assertEquals(34,test2.getID());
		
		Bloc test3 = new Bloc("BLOC-E456");
		assertEquals(456,test3.getID());
	}


}
