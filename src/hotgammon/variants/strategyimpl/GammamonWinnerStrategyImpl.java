package hotgammon.variants.strategyimpl;

import hotgammon.common.Color;
import hotgammon.common.GameImpl;
import hotgammon.common.Location;
import hotgammon.variants.strategy.WinnerStrategy;


public class GammamonWinnerStrategyImpl implements WinnerStrategy {

	@Override
	public Color isAnyWinner(GameImpl myGame) {
		if(myGame.getCount(Location.B_BEAR_OFF) == 15) {
			return Color.BLACK;
		}
		if(myGame.getCount(Location.R_BEAR_OFF) == 15) {
			return Color.RED;
		}
		return Color.NONE; 
	}
}
