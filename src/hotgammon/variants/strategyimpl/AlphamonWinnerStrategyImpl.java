package hotgammon.variants.strategyimpl;

import hotgammon.common.Color;
import hotgammon.common.GameImpl;
import hotgammon.variants.strategy.WinnerStrategy;

public class AlphamonWinnerStrategyImpl implements WinnerStrategy {

	@Override
	public Color isAnyWinner(GameImpl myGame) {
		if (myGame.getNumberOfTurn() == 6) return Color.RED;
        return Color.NONE; 

	}

}
