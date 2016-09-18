package common.game;

import hotgammon.common.GameImplParametric;
import hotgammon.common.GameImplParametric.Validator;
import hotgammon.common.GameImplParametric.Winner;
import hotgammon.common.GameImplParametric.DiceRoll;
import hotgammon.common.GameImplParametric.StartPosition;
import hotgammon.common.Color;
import hotgammon.common.Location;

import org.junit.*;

import static org.junit.Assert.*;


public class TestGameImplParam {
	GameImplParametric game = new GameImplParametric(Validator.ALPHAMON_VALIDATOR, Winner.ALPHAMON_WINNER, DiceRoll.ALPHAMON_DICE, StartPosition.ALPHAMON_START);
	Validator validator;
	Winner winner;
	DiceRoll dice_roll;
	StartPosition start_pos;
	
	@Test public void cannotMoveFromEmptyLocation() {
	      game.newGame();
	      game.nextTurn();
	      assertFalse("Should not be possible", game.move(Location.B7, Location.B10));
	}
	
	@Test public void aThirdMoveIsIllegal() {
	      game.newGame();
	      game.nextTurn();
	      assertTrue("Should be possible", game.move(Location.R1, Location.R2));
	      assertTrue("Should be possible", game.move(Location.R1, Location.R2));
	      assertFalse("Should not be possible", game.move(Location.R12, Location.R2));
	}
	
	@Test public void moveFromR1toR2isValid() {
	      game.newGame();
	      game.nextTurn();
	      game.move(Location.R1, Location.R2);
	      assertEquals("Should be one", 1, game.getCount(Location.R1));
	      assertEquals("Should be black", Color.BLACK, game.getColor(Location.R1));
	      assertEquals("Should be one", 1, game.getCount(Location.R2));
	      assertEquals("Shoule be black", Color.BLACK, game.getColor(Location.R2));
	      assertEquals("Should be one", 1, game.getNumberOfMovesLeft());
	  }
	  
	 /* @Test public void moveFromR1ToB1isInvalid() {
	      game.newGame();
	      game.nextTurn();
	      assertFalse("Should not be possible", game.move(Location.R1, Location.B1));
	  }*/
	 
}	
	


