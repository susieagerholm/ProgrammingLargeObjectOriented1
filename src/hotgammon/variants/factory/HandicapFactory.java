package hotgammon.variants.factory;

import hotgammon.common.GameFactory;
import hotgammon.variants.strategy.*;
import hotgammon.variants.strategyimpl.*;

public class HandicapFactory implements GameFactory {

	@Override
	public DiceRollStrategy createDiceRollStrategy() {
		return new AlphamonDiceRollStrategyImpl();
	}

	@Override
	public MoveValidatorStrategy createMoveValidatorStrategy() {
		return new HandicapMoveValidatorStrategyImpl(new AlphamonMoveValidatorStrategyImpl(), new BetamonMoveValidatorStrategyImpl());
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
