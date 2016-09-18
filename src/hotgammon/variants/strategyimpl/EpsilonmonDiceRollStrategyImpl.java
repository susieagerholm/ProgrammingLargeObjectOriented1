package hotgammon.variants.strategyimpl;

import hotgammon.common.GameImpl;
import hotgammon.variants.strategy.DiceRollStrategy;
import java.util.Random;


public class EpsilonmonDiceRollStrategyImpl implements DiceRollStrategy {

	@Override
	public int[] rollDice(GameImpl myGame) {
		int[] my_dice = new int[2];
		Random randomGenerator = new Random();
			for (int i = 0; i < 2; i++){
				int randomInt = randomGenerator.nextInt(6);
				my_dice[i] = randomInt + 1;
		    }
		if (my_dice[0] > my_dice[1]) {
			return my_dice;
		}
		else {
			int[] new_dice = { my_dice[1] , my_dice[0] }; 
			return new_dice;
		}
	}
}
