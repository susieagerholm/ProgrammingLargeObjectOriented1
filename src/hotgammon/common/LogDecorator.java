package hotgammon.common;

public class LogDecorator extends GameNullDecorator {

	private int[] dice = new int[2];

	
 	public LogDecorator(Game game) {
 		super(game);
 	}
	
	@Override
	public boolean move(Location from, Location to) {
		if (super.move(from, to)) {
			System.out.println(super.getPlayerInTurn() + " moves (" + from + ", " + to + ")");
			return true;
		}
		return false;
	}

	@Override
	public int[] diceThrown() {
		dice = super.diceThrown();
		System.out.println("Dice rolled: " + dice[0] + "-" + dice[1]);
		return dice;
	}

}
