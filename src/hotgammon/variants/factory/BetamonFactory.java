package hotgammon.variants.factory;

import hotgammon.common.GameFactory;
import hotgammon.variants.strategy.*;
import hotgammon.variants.strategyimpl.*;

public class BetamonFactory implements GameFactory {

	@Override
	public DiceRollStrategy createDiceRollStrategy() {
		return new AlphamonDiceRollStrategyImpl();
	}

	@Override
	public MoveValidatorStrategy createMoveValidatorStrategy() {
		return new BetamonMoveValidatorStrategyImpl();
	}

	@Override
	public StartPositionStrategy createStartPositionStrategy() {
		return new AlphamonStartPositionStrategyImpl();
	}

	@Override
	public WinnerStrategy createWinnerStrategy() {
		return new AlphamonWinnerStrategyImpl();
	}

}
