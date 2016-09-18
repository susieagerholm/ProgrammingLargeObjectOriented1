package variants.handicap.game;

import static org.junit.Assert.*;
import hotgammon.common.Game;
import hotgammon.common.GameImpl;
import hotgammon.common.Location;
import hotgammon.variants.factory.HandicapFactory;
import hotgammon.variants.strategyimpl.HandicapMoveValidatorStrategyImpl;
import hotgammon.variants.strategyimpl.AlphamonMoveValidatorStrategyImpl;
import hotgammon.variants.strategyimpl.BetamonMoveValidatorStrategyImpl;

import org.junit.Before;
import org.junit.Test;


public class TestHandicap {
	private GameImpl game;
	private HandicapMoveValidatorStrategyImpl handicapMoveValidatorStrategy = new HandicapMoveValidatorStrategyImpl(new AlphamonMoveValidatorStrategyImpl(), new BetamonMoveValidatorStrategyImpl());

	@Before
	public void setUp() {
		game = new GameImpl(new HandicapFactory());
		game.newGame();
	}

	@Test
	public void noPlayerInTurnAfterNewGame() {
		assertFalse("No valid move if no player in turn", handicapMoveValidatorStrategy.isValidMove(game, Location.B1, Location.B2));
	}

	@Test
	public void blackPlayerIsAbleToMoveBackwards() {
		game.nextTurn();
		assertTrue("Black player in turn is able to move backwards", handicapMoveValidatorStrategy.isValidMove(game, Location.R12, Location.R11));
	}

	@Test
	public void redPlayerIsNotAbleToMoveBackwards() {
		game.nextTurn();
		game.nextTurn();
		assertFalse("Red player in turn is NOT able to move backwards", handicapMoveValidatorStrategy.isValidMove(game, Location.B12, Location.B11));
	}

}
