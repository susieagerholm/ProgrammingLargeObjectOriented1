package hotgammon.variants.strategyimpl;

import hotgammon.common.Game;
import hotgammon.common.Location;
import hotgammon.common.Color;
import hotgammon.variants.strategy.MoveValidatorStrategy;


public class GammamonMoveValidatorStrategyImpl implements MoveValidatorStrategy{

	@Override
	public boolean isValidMove(Game myGame, Location from, Location to) {
		
		if (myGame.getNumberOfMovesLeft() == 0) return false; // No move is allowed
        if (myGame.getColor(from) != myGame.getPlayerInTurn()) return false; // Trying to move an oppenents checker
        if (to == Location.B_BAR || to == Location.R_BAR ) {// Trying to move to places we don't support
                return false;
            }
        
        if (myGame.getColor(to) != myGame.getPlayerInTurn() && myGame.getCount(to) > 0) return false; // Trying to move to a location occupied by the opponent
        	
		return true;
	}
	
	

}
