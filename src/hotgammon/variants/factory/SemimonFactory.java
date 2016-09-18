package hotgammon.variants.factory;

import hotgammon.common.GameFactory;
import hotgammon.variants.strategy.DiceRollStrategy;
import hotgammon.variants.strategy.MoveValidatorStrategy;
import hotgammon.variants.strategy.StartPositionStrategy;
import hotgammon.variants.strategy.WinnerStrategy;
import hotgammon.variants.strategyimpl.AlphamonStartPositionStrategyImpl;
import hotgammon.variants.strategyimpl.BetamonMoveValidatorStrategyImpl;
import hotgammon.variants.strategyimpl.EpsilonmonDiceRollStrategyImpl;
import hotgammon.variants.strategyimpl.GammamonWinnerStrategyImpl;

public class SemimonFactory implements GameFactory {

	@Override
	public DiceRollStrategy createDiceRollStrategy() {
		return new EpsilonmonDiceRollStrategyImpl();
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
		return new GammamonWinnerStrategyImpl();
	}

}
