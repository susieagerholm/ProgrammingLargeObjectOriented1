

package hotgammon.common;
import static java.lang.Math.abs;
import hotgammon.variants.strategy.*;

import java.util.*;

public class GameImplParametric implements Game {
    private Color playerInTurn; /* The color of the player currently in turn */
    private Map<Location, Color> locationColors; /* Map from Locations to colors */
    private Map<Location, Integer> locationCount; /* Map from Locations to checker count */
    private int turn = 0; /* The current turn */
    private int movesLeft; /* Moves left in the current turn */
    private Validator val;
    private Winner win;
    private DiceRoll dr;
    private StartPosition sp;
 
    public GameImplParametric(Validator validator, Winner winner, DiceRoll dice_roll, StartPosition start_pos) {
    	this.val = validator;
    	this.win = winner;
    	this.dr = dice_roll;
    	this.sp = start_pos;
    }
    
    /** Reset the entire game to start from scratch. 
       * No player is in turn, and the game awaits a
       * call to nextTurn to start a game.
       */
    public void newGame() {
        playerInTurn = Color.NONE;
        locationColors = new HashMap<Location, Color>();
        locationCount = new HashMap<Location, Integer>(); 
        
        if(this.sp == StartPosition.ALPHAMON_START) {
        	setupAlphamonStartPosition();
        }
        else {
        	setupZetamonStartPosition();
        }
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
    	//Common validation
    	if (getNumberOfMovesLeft() == 0) return false; // No move is allowed
        if (getColor(from) != getPlayerInTurn()) return false; // Trying to move an opponents checker
    	
        //Alphamon validation
    	if (this.val == Validator.ALPHAMON_VALIDATOR) {
    		performAlphamonValidation(from, to);
    	}
    	 //Batamon validation
    	if (this.val == Validator.BETAMON_VALIDATOR) {
    		performBetamonValidation(from, to);
    	}
    	 //Gammamon validation
    	if (this.val == Validator.GAMMAMON_VALIDATOR) {
    		performGammamonValidation(from, to);
    	}
    	 //Handicap validation
    	if (this.val == Validator.HANDICAP_VALIDATOR) {
    		if (getPlayerInTurn() == Color.BLACK) { 
    			performAlphamonValidation(from, to);   				
    		}
    		else if (getPlayerInTurn() == Color.RED){
    			performBetamonValidation(from, to);
    		}
    		else {
    			return false;
    		}
    	}
    	
        /*
         * Move pieces when move is validated
         */
        
    	initializeLocationIfNeeded(to);
    	
        locationCount.put(from, locationCount.get(from) - 1); // Decrement the count of checker in the from location
        if (getCount(from) == 0) { // If the checker count in from is 0 remove its color.
            locationColors.put(from, Color.NONE);
        }
      //ADDITION TO EXISTING CODE: Since validator is handling that color change can only happen according to rules, this modification  is considered OK
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
        return true; 
        	
        	
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
    	if (this.dr == DiceRoll.ALPHAMON_DICE) {
    		return performAlphamonDiceRoll(); 
       	}
    	if (this.dr == DiceRoll.EPSILONMON_DICE) {
    		return performEpsilonmonDiceRoll();
    	} 
    	else {
    		return new int[]{1,2};
    	}
    }

    public int[] diceValuesLeft() { 
        if (movesLeft == 2) return diceThrown();
        if (movesLeft == 1) return new int[]{diceThrown()[1]};
        return new int []{}; 
    }

    public Color winner() {
    	if(this.win == Winner.ALPHAMON_WINNER) {
    		return findAlphamonWinner();
    	}
    	else {
    		return findGammamonWinner();
    	}
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
    
    public void addObserver(GameObserver observer) {} ;
    
    private boolean performAlphamonValidation(Location to, Location from) {
    	// Trying to move to places we don't support
    	if (to == Location.B_BAR || to == Location.R_BAR || to == Location.R_BEAR_OFF || to == Location.B_BEAR_OFF) {
    		System.out.println("Falling for test1");
    		return false;
        }
    	// Trying to move to a location occupied by the opponent    
        if (getColor(to) != getPlayerInTurn() && getCount(to) > 0) {
        	System.out.println("Falling for test2");
        	return false;
        }
         
        return true;
    }

    private boolean performBetamonValidation(Location to, Location from) { 
    	//Negative tests
    	if (to == Location.B_BAR || to == Location.R_BAR) return false ;// Trying to move to places we don't support
    			
    	if (getPlayerInTurn() == Color.BLACK) {
    		if(Location.distance(from, to) <= 0) { return false; }
    	}
    			
    	if (getPlayerInTurn() == Color.RED) {
    		if(Location.distance(from, to) >= 0) { return false; }
    	}
    			
    	if (getPlayerInTurn() != getColor(to) && getCount(to) > 1) return false;
    			
    	if (getPlayerInTurn() == Color.RED) {
    		if(getCount(Location.R_BAR) != 0 && !from.equals(Location.R_BAR)) { return false; }
    	}
    	else {
    		if(getCount(Location.B_BAR) != 0 && !from.equals(Location.B_BAR)) { return false; }
    	}
    			
    	if(to.equals(Location.B_BEAR_OFF) || to.equals(Location.R_BEAR_OFF)) {
    				isLegalToBearOff(this, getPlayerInTurn());
    	}
    			
    	//Positive tests
    	if(getNumberOfMovesLeft() == 2) {
    		if(abs(Location.distance(from, to)) == diceThrown()[0]) return true;
    	}
    	else {
    		if(abs(Location.distance(from, to)) == diceThrown()[1]) return true;
    	}   
    	
    	return false;

    }

    //Helper methods for Betamon Validation
    private boolean isLegalToBearOff(GameImplParametric myGame, Color currPlayer) {
		Location[] inner; 
		inner = getPlayerInnerTable(currPlayer);
		int sum = 0;		
		for ( Location l : inner) {
			if (myGame.getColor(l) == currPlayer) {
				sum += myGame.getCount(l);
			}
		}
		if(sum == 15) {
			return true;
		}
		return false;
				
	}
	
	private Location[] getPlayerInnerTable(Color currPlayer) {
		Location[] red =  {Location.R1, Location.R2, Location.R3, Location.R4, Location.R5, Location.R6, Location.R_BEAR_OFF}; 
		Location[] black = {Location.B1, Location.B2, Location.B3, Location.B4, Location.B5, Location.B6, Location.B_BEAR_OFF};
		
		if(currPlayer == Color.RED) {
			return red;
		} 
		else {
			return black; 
		}
	}	
	
    
    
    private boolean performGammamonValidation(Location to, Location from) { 
    	if (to == Location.B_BAR || to == Location.R_BAR ) return false; // Trying to move to places we don't support
     
    	if (getColor(to) != getPlayerInTurn() && getCount(to) > 0) return false; // Trying to move to a location occupied by the opponent
     	
		return true;
    	
    }
    
    private Color findAlphamonWinner() { return Color.RED; }
    private Color findGammamonWinner() { return Color.RED; }
    
    private int[] performAlphamonDiceRoll() { 
    	final int[][] rolls = {{5,6}, {1,2}, {3,4}}; /* The dice-rolls available in alpha-mon */
    	return rolls[getNumberOfTurn() % 3];
    	
    }
    private int[] performEpsilonmonDiceRoll() { return new int[]{}; }
    
    private void setupAlphamonStartPosition() { 
    	 //We update the two maps with the initial checker configuration
		putColor(Location.R1, Color.BLACK);
        putCount(Location.R1, 2);
        putColor(Location.B1, Color.RED);
        putCount(Location.B1, 2);
        putColor(Location.B6, Color.BLACK);
        putCount(Location.B6, 5);
        putColor(Location.R6, Color.RED);
        putCount(Location.R6, 5);
        putColor(Location.B12, Color.RED);
        putCount(Location.B12, 5);
        putColor(Location.R12, Color.BLACK);
        putCount(Location.R12, 5);
        putColor(Location.R8, Color.RED);
        putCount(Location.R8, 3);
        putColor(Location.B8, Color.BLACK);
        putCount(Location.B8, 3);
        
        System.out.println("LocationColors: " + new PrettyPrintingMap<Location, Color>(locationColors));
        
        System.out.println("LocationCount: " + new PrettyPrintingMap<Location, Integer>(locationCount));
    }
    
    private void setupZetamonStartPosition() { }
    
    public enum Validator {
    	ALPHAMON_VALIDATOR,
    	BETAMON_VALIDATOR,
    	GAMMAMON_VALIDATOR,
    	HANDICAP_VALIDATOR
    }
    
    public enum Winner {
    	ALPHAMON_WINNER,
    	GAMMAMON_WINNER
    }
    
    public enum DiceRoll {
    	ALPHAMON_DICE,
    	EPSILONMON_DICE
    }
    
    public enum StartPosition {
    	ALPHAMON_START,
    	ZETAMON_START
    }
    public class PrettyPrintingMap<K, V> {
        private Map<K, V> map;

        public PrettyPrintingMap(Map<K, V> map) {
            this.map = map;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator<Map.Entry<K, V>> iter = map.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<K, V> entry = iter.next();
                sb.append(entry.getKey());
                sb.append('=').append('"');
                sb.append(entry.getValue());
                sb.append('"');
                if (iter.hasNext()) {
                    sb.append(',').append(' ');
                }
            }
            return sb.toString();

        }
    }

}



