package hotgammon.variants.strategyimpl;

import hotgammon.common.Color;
import hotgammon.common.GameImpl;
import hotgammon.common.Location;
import hotgammon.variants.strategy.StartPositionStrategy;

public class ZetamonStartPositionStrategyImpl implements StartPositionStrategy {

	@Override
	public void checkersSetup(GameImpl myGame) {
        //We update the two maps with the initial checker configuration
		myGame.putColor(Location.R1, Color.BLACK);
        myGame.putCount(Location.R1, 1);
        myGame.putColor(Location.R2, Color.BLACK);
        myGame.putCount(Location.R2, 1);
        myGame.putColor(Location.R3, Color.BLACK);
        myGame.putCount(Location.R3, 1);
        myGame.putColor(Location.B1, Color.RED);
        myGame.putCount(Location.B1, 1);
        myGame.putColor(Location.B2, Color.RED);
        myGame.putCount(Location.B2, 1);
        myGame.putColor(Location.B3, Color.RED);
        myGame.putCount(Location.R3, 1);
	}

}
