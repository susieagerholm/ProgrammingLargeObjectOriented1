package hotgammon.variants.strategyimpl;

import hotgammon.common.Color;
import hotgammon.common.Game;
import hotgammon.common.Location;
import hotgammon.variants.strategy.MoveValidatorStrategy;
import static java.lang.Math.abs;

public class BetamonMoveValidatorStrategyImpl implements MoveValidatorStrategy {

	@Override
	public boolean isValidMove(Game myGame, Location from, Location to) {
		
		//Negative tests
		if (myGame.getNumberOfMovesLeft() == 0) return false; // No move is allowed
		     	
		if (myGame.getColor(from) != myGame.getPlayerInTurn()) return false; // Trying to move an oppenents checker
		
		if (to == Location.B_BAR || to == Location.R_BAR) { // Trying to move to places we don't support
			   return false;
		}
		
		if (myGame.getPlayerInTurn() == Color.BLACK) {
			if(Location.distance(from, to) <= 0) { return false; }
		}
		
		if (myGame.getPlayerInTurn() == Color.RED) {
			if(Location.distance(from, to) >= 0) { return false; }
		}
		
		if (myGame.getPlayerInTurn() != myGame.getColor(to) && myGame.getCount(to) > 1) {
			return false;
		}
		
		if (myGame.getPlayerInTurn() == Color.RED) {
			if(myGame.getCount(Location.R_BAR) != 0 && !from.equals(Location.R_BAR)) { return false; }
		}
		else {
			if(myGame.getCount(Location.B_BAR) != 0 && !from.equals(Location.B_BAR)) { return false; }
		}
		
		if(to.equals(Location.B_BEAR_OFF) || to.equals(Location.R_BEAR_OFF)) {
			isLegalToBearOff(myGame, myGame.getPlayerInTurn());
		}
		
		//Positive tests
		if(myGame.getNumberOfMovesLeft() == 2) {
			if(abs(Location.distance(from, to)) == myGame.diceThrown()[0]) 	{
				return true;
			}
					
		}
		else {
			if(abs(Location.distance(from, to)) == myGame.diceThrown()[1]) {
				return true;
			}
		}
			
			        
		return false;
	}
	
	private boolean isLegalToBearOff(Game myGame, Color currPlayer) {
		Location[] inner; 
		inner = getPlayerInnerTable(currPlayer);
		int sum = 0;		
		for ( Location l : inner) {
			if (myGame.getColor(l) == currPlayer) {
				sum += myGame.getCount(l);
			}
		}
		if(sum == 15) {
			return true;
		}
		return false;
				
	}
	
	private Location[] getPlayerInnerTable(Color currPlayer) {
		Location[] red =  {Location.R1, Location.R2, Location.R3, Location.R4, Location.R5, Location.R6, Location.R_BEAR_OFF}; 
		Location[] black = {Location.B1, Location.B2, Location.B3, Location.B4, Location.B5, Location.B6, Location.B_BEAR_OFF};
		
		if(currPlayer == Color.RED) {
			return red;
		} 
		else {
			return black; 
		}
	}	
	

}
