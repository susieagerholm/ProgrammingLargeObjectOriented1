package common.game;

import hotgammon.common.Game;
import hotgammon.common.GameImpl;
import hotgammon.common.Location;
import hotgammon.common.LogDecorator;
import hotgammon.variants.factory.AlphamonFactory;

import org.junit.Before;
import org.junit.Test;

public class TestLogDecorator {

	private Game myGame;
	private LogDecorator myGameWithLog;
	
	@Before
  public void setup() {
      myGame = new GameImpl(new AlphamonFactory());
	    myGameWithLog = new LogDecorator(myGame);
	    
	    myGame.newGame();
	    myGame.nextTurn();
  }
	
 	@Test
  public void movesWithLog() {
      myGameWithLog.diceThrown();
      myGameWithLog.move(Location.R1, Location.R3);
      myGameWithLog.move(Location.R1, Location.R2);
      myGameWithLog.nextTurn();
      myGameWithLog.diceThrown();
      myGameWithLog.move(Location.B12, Location.R10);
      myGameWithLog.move(Location.R10, Location.R6);
      myGameWithLog.nextTurn();
      myGameWithLog.diceThrown();
      myGameWithLog.move(Location.B8, Location.B3);
      myGameWithLog.move(Location.R2, Location.R9);
      myGameWithLog.nextTurn();
      myGameWithLog.diceThrown();
	}

	@Test
  public void moveWithOutAndWithLog() {
      int[] result = myGameWithLog.diceThrown();
      myGame.move(Location.R1, Location.R3);
      myGameWithLog.move(Location.R1, Location.R2);
	}
}
