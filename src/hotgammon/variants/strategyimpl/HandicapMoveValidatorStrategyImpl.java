package hotgammon.variants.strategyimpl;

import hotgammon.common.Color;
import hotgammon.common.Game;
import hotgammon.common.Location;
import hotgammon.variants.strategy.MoveValidatorStrategy;


public class HandicapMoveValidatorStrategyImpl implements MoveValidatorStrategy {

	private MoveValidatorStrategy blackHandicap, redHandicap, currentState;

	public HandicapMoveValidatorStrategyImpl(MoveValidatorStrategy blackHandicap, MoveValidatorStrategy redHandicap) {
		this.blackHandicap = blackHandicap;
		this.redHandicap = redHandicap;
	}
	
	@Override
	public boolean isValidMove(Game game, Location from, Location to) {
		
		Color player = game.getPlayerInTurn();
		
		if (player == Color.BLACK) {
			currentState = blackHandicap;
		}
		else if (player == Color.RED) {
			currentState = redHandicap;
		}
		else {
			return false;
		}
		
		return currentState.isValidMove(game, from, to);
	}
}
