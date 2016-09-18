package stubs;

import hotgammon.common.GameFactory;
import hotgammon.variants.strategy.*;
import hotgammon.variants.strategyimpl.*;

public class TestStubFixedValueFactory implements GameFactory {

  private int die1, die2;
	
  public TestStubFixedValueFactory(int die1, int die2) {
      this.die1 = die1;
      this.die2 = die2;
  }

  @SuppressWarnings("unused")
  private  TestStubFixedValueFactory() {}
  
	@Override
	public DiceRollStrategy createDiceRollStrategy() {
		return new FixedValueStrategyImpl(die1, die2);
	}

	@Override
	public MoveValidatorStrategy createMoveValidatorStrategy() {
		return new AlphamonMoveValidatorStrategyImpl();
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
