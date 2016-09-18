package hotgammon.variants.strategyimpl;

import hotgammon.common.GameImpl;
import hotgammon.common.Color;
import hotgammon.common.Location;
import hotgammon.variants.strategy.StartPositionStrategy;

public class AlphamonStartPositionStrategyImpl implements StartPositionStrategy {

	@Override
	public void checkersSetup(GameImpl myGame) {
        //We update the two maps with the initial checker configuration
		myGame.putColor(Location.R1, Color.BLACK);
        myGame.putCount(Location.R1, 2);
        myGame.putColor(Location.B1, Color.RED);
        myGame.putCount(Location.B1, 2);
        myGame.putColor(Location.B6, Color.BLACK);
        myGame.putCount(Location.B6, 5);
        myGame.putColor(Location.R6, Color.RED);
        myGame.putCount(Location.R6, 5);
        myGame.putColor(Location.B12, Color.RED);
        myGame.putCount(Location.B12, 5);
        myGame.putColor(Location.R12, Color.BLACK);
        myGame.putCount(Location.R12, 5);
        myGame.putColor(Location.R8, Color.RED);
        myGame.putCount(Location.R8, 3);
        myGame.putColor(Location.B8, Color.BLACK);
        myGame.putCount(Location.B8, 3);
	}

}
