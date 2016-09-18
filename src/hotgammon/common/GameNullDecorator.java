package hotgammon.common;

public class GameNullDecorator implements Game {
	private Game myGame;
	
 	public GameNullDecorator(Game game) {
 		this.myGame = game;
 	}
	
	@Override
	public void newGame() {
		myGame.newGame();
	}

	@Override
	public void nextTurn() {
		myGame.nextTurn();
	}

	@Override
	public boolean move(Location from, Location to) {
		return myGame.move(from, to);
	}

	@Override
	public Color getPlayerInTurn() {
		return myGame.getPlayerInTurn();
	}

	@Override
	public int getNumberOfMovesLeft() {
		return myGame.getNumberOfMovesLeft();
	}

	@Override
	public int[] diceThrown() {
		return myGame.diceThrown();
	}

	@Override
	public int[] diceValuesLeft() {
		return myGame.diceValuesLeft();
	}

	@Override
	public Color winner() {
		return myGame.winner();
	}

	@Override
	public Color getColor(Location location) {
		return myGame.getColor(location);
	}

	@Override
	public void putColor(Location location, Color color) {
		myGame.putColor(location, color);
	}

	@Override
	public int getCount(Location location) {
		return myGame.getCount(location);
	}

	@Override
	public void putCount(Location location, int count) {
		myGame.putCount(location, count);
	}

	@Override
	public int getNumberOfTurn() {
		return myGame.getNumberOfTurn();
	}

	@Override
	public void addObserver(GameObserver observer) {
		myGame.addObserver(observer);
	}

}
