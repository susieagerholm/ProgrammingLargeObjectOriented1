package hotgammon.variants.strategy;

import hotgammon.common.Color;
import hotgammon.common.GameImpl;

public interface WinnerStrategy {
	public Color isAnyWinner(GameImpl myGame);
}
