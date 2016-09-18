package variants.semimon.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import hotgammon.common.Color;
import hotgammon.common.Game;
import hotgammon.common.GameImpl;
import hotgammon.common.Location;
import hotgammon.variants.factory.SemimonFactory;
//import hotgammon.variants.factory.AlphamonFactory;

import org.junit.Before;
import org.junit.Test;

public class TestSemimon {
	private Game game;
	
	@Before public void setup() {
	    game = new GameImpl(new SemimonFactory());
	    game.newGame();
	  }
	
	//Test that we have AlphaMon StartPositions strategy
	@Test public void initiallyTwoBlackCheckersOnR1() {
	      assertEquals("Should be two", 2, game.getCount(Location.R1));
	      assertEquals("Should be black", Color.BLACK, game.getColor(Location.R1));
	}
	
	//Test that we have EpsilonMon dice roll strategy
	@Test public void returnedArrayConsistsOfIntegersWithinValidSpace() {
		int[] result = game.diceThrown();
	    assertTrue("diceThrown returnerer et første terningekast der er lig med eller større end 1", result[0] >= 1);
	    assertTrue("diceThrown returnerer et første terningekast der er lig med eller mindre end 6", result[0] <= 6);
	    assertTrue("diceThrown returnerer et første terningekast der er lig med eller større end 1", result[1] >= 1);
	    assertTrue("diceThrown returnerer et første terningekast der er lig med eller mindre end 6", result[1] <= 6);
	}
	
	
	//Test that we have GammaMon winner strategy
	@Test public void noWinnerFoundAfterSixTurns() {
		game.nextTurn();
		game.nextTurn();
		game.nextTurn();
		game.nextTurn();
		game.nextTurn();
		game.nextTurn();
		assertEquals("No winner is found", game.winner(), Color.NONE);
	}
	
	//Test that we have BetaMon strategy
	@Test
	public void redPlayerIsNotAbleToMoveBackwards() {
		game.nextTurn();
		assertFalse("Black player in turn is NOT able to move backwards", game.move(Location.R12, Location.R11));
	}


}
