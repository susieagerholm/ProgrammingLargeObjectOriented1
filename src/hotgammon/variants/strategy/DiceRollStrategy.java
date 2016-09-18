package hotgammon.variants.strategy;

import hotgammon.common.GameImpl;

public interface DiceRollStrategy {
	public int[] rollDice(GameImpl myGame);
}
