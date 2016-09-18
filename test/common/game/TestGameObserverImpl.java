package common.game;

import org.junit.*;

import static org.junit.Assert.*;
import hotgammon.common.GameImpl;
import hotgammon.common.GameObserver;
import hotgammon.common.Location;
import hotgammon.variants.factory.AlphamonFactory;

public class TestGameObserverImpl implements GameObserver{
	private GameImpl game;
	private GameObserver tgo;
	private static int diceRollCounter = 0;
	private static int checkerMoveCounter = 0; 
		
	@Before
	public void SetUp() {
		game = new GameImpl(new AlphamonFactory());
	}
	
	@Test
	public void addTwoNewGameObserver(){
		 game.addObserver(this);
		 game.addObserver(tgo);
		 assertEquals("Observer count bør være 2", 2, game.getNoObservers());
	}
	
	@Test
	public void notifyObserverWhenDiceIsThrown() {
		 game.addObserver(this);
		 game.diceThrown();
		 assertEquals("Observer should be notified on dice roll", 1, diceRollCounter);
	}
	
	@Test
	public void notifyObserverWhenCheckerIsMoved() {
		 game.addObserver(this);
		 game.newGame();
		 game.nextTurn();
		 game.diceThrown();
		 game.move(Location.R1, Location.R3);
		 assertEquals("CheckerMoveCounter incremented by 1", 1, checkerMoveCounter);
		 game.move(Location.R1, Location.R2);
		 assertEquals("CheckerMoveCounter incremented by 1 + 1", 2, checkerMoveCounter);
	}

	@Override
	public void checkerMove(Location from, Location to) {
		checkerMoveCounter += 1;
		
	}

	@Override
	public void diceRolled(int[] values) {
		diceRollCounter += 1;
	}

}
