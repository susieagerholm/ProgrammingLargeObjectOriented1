package hotgammon.variants.strategyimpl;

import hotgammon.common.GameImpl;
import hotgammon.variants.strategy.DiceRollStrategy;


public class AlphamonDiceRollStrategyImpl implements DiceRollStrategy {

    private int[][] rolls = {{5,6}, {1,2}, {3,4}}; /* The dice-rolls available in alpha-mon */
	
	@Override
	public int[] rollDice(GameImpl myGame) {
        return rolls[myGame.getNumberOfTurn() % 3];
	}

}
