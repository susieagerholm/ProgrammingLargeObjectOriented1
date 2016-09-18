package hotgammon.common;

import hotgammon.variants.strategy.*;
 
public interface GameFactory {
	public DiceRollStrategy createDiceRollStrategy();
	public MoveValidatorStrategy createMoveValidatorStrategy();
	public StartPositionStrategy createStartPositionStrategy();
	public WinnerStrategy createWinnerStrategy();
}
