package stubs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import hotgammon.common.Game;
import hotgammon.common.GameFactory;
import hotgammon.common.GameImpl;
import hotgammon.variants.*;

import org.junit.Before;
import org.junit.Test;


public class TestFixedValueTestStub {
	
	private Game myGame;
  
	//diceValuesLeft performs according to spec with input from new diceThrown - decrement array 
	@Test public void fixedValueStrategySetsDefaultResultArrayAsExpected() {
		TestStubFixedValueFactory myTestStubFactory = new TestStubFixedValueFactory(4, 3);
		assertNotNull("test stub factory is NULL", myTestStubFactory);
		myGame = new GameImpl(myTestStubFactory);
		assertNotNull("My game is NULL", myGame);

		int[] result = myGame.diceThrown();
		assertEquals("Den første terningeværdi fra test stub default bør være 4", 4, result[0]);
		assertEquals("Den anden terningeværdi fra test stub default bør være 3", 3, result[1]);
		assertEquals("Test stub returnerer array med præcis to værdier", 2, result.length);
	}	 	 
		 
  //diceValuesLeft performs according to spec with input from new diceThrown - decrement array 
	@Test public void fixedValueStrategySetsResultArrayAsExpected() {
      myGame = new GameImpl(new TestStubFixedValueFactory(1, 5));
      myGame.newGame();
	    myGame.nextTurn();

      int[] result = myGame.diceThrown();
      assertEquals("Den første terningeværdi fra test stub efter setter bør være 5", 5, result[0]);
      assertEquals("Den anden terningeværdi fra test stub efter setter være 1", 1, result[1]);
      assertEquals("Test stub returnerer array med præcis to værdier", 2, result.length);
	}	 	 

}
