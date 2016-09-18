package hotgammon.variants.strategy;

import hotgammon.common.Game;
import hotgammon.common.Color;
import hotgammon.common.Location;

public interface MoveValidatorStrategy {
	
	public boolean isValidMove(Game game,  Location from, Location to);
	
	//(int movesLeft, Color playerInTurn, Location to,Color colorFromLocation, Color colorToLocation, int toCount);
}
