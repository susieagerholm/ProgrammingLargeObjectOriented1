
package hotgammon.common;
import hotgammon.variants.strategy.*;

import java.util.*;

public class GameImpl implements Game {
    private Color playerInTurn; /* The color of the player currently in turn */
    private Map<Location, Color> locationColors; /* Map from Locations to colors */
    private Map<Location, Integer> locationCount; /* Map from Locations to checker count */
    private int turn = 0; /* The current turn */
    private int movesLeft; /* Moves left in the current turn */
    private MoveValidatorStrategy moveValidator;
    private WinnerStrategy winnerStrategy;
    private DiceRollStrategy diceRollStrategy;
    private StartPositionStrategy startPositionStrategy;
    private GameFactory gameFactory;
    private List<GameObserver> go_list;
    
    public GameImpl(GameFactory gameFactory) {
    	this.gameFactory = gameFactory;
    	this.moveValidator = gameFactory.createMoveValidatorStrategy(); 
    	this.winnerStrategy = gameFactory.createWinnerStrategy();
    	this.diceRollStrategy = gameFactory.createDiceRollStrategy();
    	this.startPositionStrategy = gameFactory.createStartPositionStrategy();
    	this.go_list = new ArrayList<GameObserver>();
    }
    
    /** Reset the entire game to start from scratch. 
       * No player is in turn, and the game awaits a
       * call to nextTurn to start a game.
       */
    public void newGame() {
        playerInTurn = Color.NONE;
        locationColors = new HashMap<Location, Color>();
        locationCount = new HashMap<Location, Integer>(); 

		/*        
        //We update the two maps with the initial checker configuration
        locationColors.put(Location.R1, Color.BLACK);
        locationCount.put(Location.R1, 2);
        locationColors.put(Location.B1, Color.RED);
        locationCount.put(Location.B1, 2);
        locationColors.put(Location.B6, Color.BLACK);
        locationCount.put(Location.B6, 5);
        locationColors.put(Location.R6, Color.RED);
        locationCount.put(Location.R6, 5);
        locationColors.put(Location.B12, Color.RED);
        locationCount.put(Location.B12, 5);
        locationColors.put(Location.R12, Color.BLACK);
        locationCount.put(Location.R12, 5);
        locationColors.put(Location.R8, Color.RED);
        locationCount.put(Location.R8, 3);
        locationColors.put(Location.B8, Color.BLACK);
        locationCount.put(Location.B8, 3);
*/
        startPositionStrategy.checkersSetup(this);
    }

    public void nextTurn() {
        turn++; // Increment the turn count 
        movesLeft = 2; // reset moves left
        if (playerInTurn == Color.BLACK) {
            playerInTurn = Color.RED;
        } else {
            playerInTurn = Color.BLACK;
        }
    }
    
    public boolean move(Location from, Location to) {
        /*
         * Validate move via Strategy pattern
         * All validation moved to Strategy for optimal cohesion - drawback: verbose method signature...
    	*/
    	if (moveValidator.isValidMove(this, from, to) == false) { 
    		
    		return false;
    	}
 
    	
        /*
         * Move pieces when move is validated
         */
        
    	initializeLocationIfNeeded(to);
    	
        locationCount.put(from, locationCount.get(from) - 1); // Decrement the count of checker in the from location
        if (getCount(from) == 0) { // If the checker count in from is 0 remove its color.
            locationColors.put(from, Color.NONE);
        }
      //ADDITION TO EXISTING CODE: Since Validator is handling that color change can only happen according to rules, this modification  is considered OK
        if(getColor(to) == getPlayerInTurn() || getColor(to) == Color.NONE) { 
        	locationCount.put(to, locationCount.get(to) + 1); // Increment the count of the to location - IF move to empty or own
        	
        }
        else { //opponent checker is beaten 
        	//update opponent bar
        	if(getPlayerInTurn() == Color.RED) {
        		initializeLocationIfNeeded(Location.B_BAR);
        		locationCount.put(Location.B_BAR, locationCount.get(Location.B_BAR) + 1);
        		locationColors.put(Location.B_BAR, Color.BLACK);         
        		
        	}
        	else {
        		initializeLocationIfNeeded(Location.R_BAR);
        		locationCount.put(Location.R_BAR, locationCount.get(Location.R_BAR) + 1);
        		locationColors.put(Location.R_BAR, Color.RED);         
        	}
        		
        }
        //MODIFICATION ENDED
        locationColors.put(to, playerInTurn);         
        movesLeft--; // Decrement movesLeft
        notifyObservers(from, to);
        
        return true; 
    }

    private void notifyObservers(Location from, Location to) {
    	for (GameObserver go : go_list) {
    		go.checkerMove(from, to);
    	}
	}
    
    private void notifyObservers(int[] my_vals) {
    	 for (GameObserver go : go_list) {
    		 go.diceRolled(my_vals);
    	 }
	}

    private void initializeLocationIfNeeded(Location to) {
		if (!locationCount.containsKey(to)) { // This location has not been used before, hence we initialize its count
            locationCount.put(to, 0);
        }
        if (!locationColors.containsKey(to)) { //This location is not colored, hence we color it
            locationColors.put(to, getPlayerInTurn());
        }
		
	}

	public Color getPlayerInTurn() {
        return playerInTurn;
    }

    public int getNumberOfMovesLeft() { 
        return movesLeft; 
    }

    public int[] diceThrown() {
    	int [] values = diceRollStrategy.rollDice(this);
    	notifyObservers(values);
    	return values;
    }

    public int[] diceValuesLeft() { 
        if (movesLeft == 2) return diceThrown();
        if (movesLeft == 1) return new int[]{diceThrown()[1]};
        return new int []{}; 
    }

    public Color winner() {
        return winnerStrategy.isAnyWinner(this);
    }

    public Color getColor(Location location) { 
        if (!locationColors.containsKey(location)) return Color.NONE;
        return this.locationColors.get(location);
    }

    public void putColor(Location location, Color color) {
        locationColors.put(location, color);
    }
    
    public int getCount(Location location) {
        if (this.locationCount.containsKey(location)) {
            return this.locationCount.get(location);   
        } else {
            return 0;
        }
    }
    
    public void putCount(Location location, int count) {
    	locationCount.put(location, count);
    }
    
    public int getNumberOfTurn() {
    	return turn;
    }
    
    public void addObserver(GameObserver observer) {
    	go_list.add(observer);
    } 
    
    public int getNoObservers() {
    	return go_list.size();
    }
}
