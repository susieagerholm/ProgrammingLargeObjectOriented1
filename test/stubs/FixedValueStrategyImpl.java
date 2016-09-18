package stubs;

import hotgammon.common.GameImpl;
import hotgammon.variants.strategy.DiceRollStrategy;

public class FixedValueStrategyImpl implements DiceRollStrategy {
	//create result array
	private int result[] = new int[2];

	private FixedValueStrategyImpl() {}

	/** construct a test stub fixed dice values strategy.
	 *  Ensure that invariant in diceValuesLeft is maintained (line 92-93 GameImpl.java) 
	 *  POSTCONDITION:The array is sorted so the largest die value is first in the array 
	 *  @param res1 Value af 1st die
   *  @param res2 Value af 2nd die
	 *  method rollDice(GameImpl).
	 */
	public FixedValueStrategyImpl(int res1, int res2) {
		if(res2 > res1) {
			result[0] = res2;
			result[1] = res1;
		}
		else {
			result[0] = res1;
			result[1] = res2;
		}
	}

	@Override
	public int[] rollDice(GameImpl myGame) {
		return result;
	}
}
