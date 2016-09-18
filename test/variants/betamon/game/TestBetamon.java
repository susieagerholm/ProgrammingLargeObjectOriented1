package variants.betamon.game;

import hotgammon.common.Color;
import hotgammon.common.GameImpl;
import hotgammon.common.Location;
//import hotgammon.common.LogDecorator;
import hotgammon.variants.strategy.MoveValidatorStrategy;
import hotgammon.variants.factory.BetamonFactory;
import static java.lang.Math.abs;



import org.junit.*;

import static org.junit.Assert.*;

public class TestBetamon {
  private MoveValidatorStrategy strategy;
  private GameImpl myGame;
//  private LogDecorator myGame;
  private BetamonFactory betamonFactory = new BetamonFactory();
  
  @Before public void setup() {
    strategy = betamonFactory.createMoveValidatorStrategy();
    myGame = new GameImpl(betamonFactory);
//	myGame = new LogDecorator(new GameImpl(betamonFactory));
    
    
    myGame.newGame();
	myGame.nextTurn();
  }
  
  @Test public void aThirdMoveIsIllegal() {
	  
	  myGame.move(Location.B1, Location.B2);
	  myGame.move(Location.B2, Location.B4);
      assertFalse("Not possible to move when movesLeft is 0", strategy.isValidMove(myGame, Location.B4, Location.B7));    
  }
   
  @Test public void aMoveToBarNotAllowed() {
	  
	  assertFalse("Not possible to move checker to bar", strategy.isValidMove(myGame, Location.B4, Location.B_BAR));    
  }
  
  @Test public void aMoveWithOpponentCheckerNotAllowed() {
  	  //Set up conditions for test
	  assertEquals("Checker in Location.from must be red", Color.RED, myGame.getColor(Location.B1));
  	  assertEquals("Player in turn is Color.BLACK", Color.BLACK, myGame.getPlayerInTurn());
      //Perform test
  	  assertFalse("Not possible to move opponent checker", strategy.isValidMove(myGame, Location.B1, Location.B2));    
  }
  
  @Test public void aMoveFromEmptyLocationNoAllowed() {
	  assertEquals("Player in turn is Color.BLACK", Color.BLACK, myGame.getPlayerInTurn());
      assertFalse("Player cannot move from empty location", strategy.isValidMove(myGame, Location.B10, Location.B9));
  }
  
  @Test public void moveBlackCheckerOneLocationAndBlackCheckerTwoLocations() {
	  assertEquals("Player in turn is Color.BLACK", Color.BLACK, myGame.getPlayerInTurn());
	  assertEquals("Should be one", 1, myGame.diceThrown()[0]);
      assertEquals("Should be two", 2, myGame.diceThrown()[1]);
      assertTrue("Player can move according to the dice", strategy.isValidMove(myGame, Location.R1, Location.R2));
      //assertTrue("Player can move according to the dice", strategy.isValidMove(myGame, Location.R1, Location.R3));
      assertFalse("Player can move according to the dice", strategy.isValidMove(myGame, Location.R1, Location.R4));
  }
  
  @Test public void moveBlackCheckerSixLocationsOnFirstTurnIsIllegal() {
	  assertEquals("Player in turn is Color.BLACK", Color.BLACK, myGame.getPlayerInTurn());
	  assertEquals("Should be one", 1, myGame.diceThrown()[0]);
      assertEquals("Should be two", 2, myGame.diceThrown()[1]);
      assertFalse("Player can only move according to the dice", strategy.isValidMove(myGame, Location.R1, Location.R7));
      
  }
  
  @Test public void ifTwoMovesLeftMoveMustEqualFirstDie() {
	  assertEquals("Two moves left", 2, myGame.getNumberOfMovesLeft());      
	  assertTrue("The move must be equal to first die", strategy.isValidMove(myGame, Location.R1,  Location.R2));
  }
  
  
  @Test public void ifOneMoveLeftTheMoveMustEqualValueOfLastDie() {
	  assertEquals("Two moves left", 2, myGame.getNumberOfMovesLeft());
	  assertTrue("Make first move..", myGame.move(Location.R1, Location.R2));
	  
	  assertEquals("One move left", 1, myGame.getNumberOfMovesLeft());
	  assertEquals("The move must be equal to value of last die", 2, abs(Location.distance(Location.R1, Location.R3)));
  }
  
  @Test public void moveWithReuseOfDieValueIsNotAllowed() {
	  assertTrue("Make first move..", myGame.move(Location.B8, Location.B7));
	  assertFalse("Second move with first move value not allowed", strategy.isValidMove(myGame, Location.B7, Location.B6));
  }
  
  @Test public void moveAllowedOnlyInRightDirection() {
	  assertFalse("Move Black Checker from R12 to R11 is illegal", strategy.isValidMove(myGame, Location.R12, Location.R11));
	  myGame.nextTurn();
	  assertEquals("Red player in turn", Color.RED, myGame.getPlayerInTurn());
	  assertFalse("Move Red Checker from R8 to R11 is illegal", strategy.isValidMove(myGame, Location.R8, Location.R11));
  }
  
  @Test public void moveToLocationWithTwoOrMoreOpponentCheckerIsNotAllowed() {
	  assertFalse("Move black checker to blocked point - ie. Location with two or more Red", strategy.isValidMove(myGame, Location.R12, Location.B12));
  }
  
  @Test public void moveToALocationWithOneOpponentCheckerShouldEliminateIncrementAndUpdateOpponentBar() {
	  myGame.move(Location.R1, Location.R2);
	  myGame.move(Location.R1, Location.R3);
	  myGame.nextTurn();
	  assertTrue("Move with red from R6 to R3 is allowed: Black checker is beaten", strategy.isValidMove(myGame, Location.R6, Location.R3));	  
	  myGame.move(Location.R6, Location.R3);
	  assertEquals("Number of checkers in to Location must be one", 1, myGame.getCount(Location.R3));
	  assertEquals("Current color is red", Color.RED, myGame.getColor(Location.R3));
	  assertEquals("No. of checkers in Black Bar", 1, myGame.getCount(Location.B_BAR));
	  myGame.move(Location.R6, Location.R2);
	  assertEquals("Current color is red", Color.RED, myGame.getColor(Location.R2));
	  assertEquals("No. of checkers in Black Bar", 2, myGame.getCount(Location.B_BAR));
	  assertEquals("Color of chekcers in B_BAR", Color.BLACK, myGame.getColor(Location.B_BAR));
	  
  }
  
  @Test public void moveFromBarToEmptyLocationIsLegal () {
	  myGame.move(Location.R1, Location.R2);
	  myGame.move(Location.R1, Location.R3);
	  myGame.nextTurn();
	  myGame.move(Location.R6, Location.R3); //Red beats black to bar
	  myGame.move(Location.R6, Location.R2); //Red beats black to bar = 2 black in B_BAR
	  myGame.nextTurn();
	  assertTrue("Black can move from BAR_B to empty Location R5", strategy.isValidMove(myGame, Location.B_BAR, Location.R5));
	  
  }
  
  @Test public void moveFromBarToBlockedLocationIsIllegal () {
	  myGame.move(Location.R1, Location.R2);
	  myGame.move(Location.R1, Location.R3);
	  myGame.nextTurn();
	  myGame.move(Location.R6, Location.R3);
	  myGame.move(Location.R6, Location.R2);
	  myGame.nextTurn();
	  myGame.move(Location.B_BAR, Location.R5);
	  assertFalse("Black must not be allowed to move to blocked location", strategy.isValidMove(myGame, Location.B_BAR, Location.R6));
	  myGame.move(Location.B_BAR, Location.R6);
	  myGame.nextTurn();
	  assertEquals("Red in turn after blacks second move is discarded by validator...", Color.RED, myGame.getPlayerInTurn());
	  
  }
  	
  @Test public void movingOtherThanCheckersInBarIsNotAllowed() {
	  myGame.move(Location.R1, Location.R2);
	  myGame.move(Location.R1, Location.R3);
	  myGame.nextTurn();
	  myGame.move(Location.R6, Location.R3);
	  myGame.move(Location.R6, Location.R2);
	  myGame.nextTurn();
	  myGame.move(Location.B_BAR, Location.R5);
	  assertFalse("Black must not be allowed to move other than checker in Bar", strategy.isValidMove(myGame, Location.R5, Location.R11));
  }
  
  @Test public void movingFromBarMustBeToLocationInOpponentInnerTable() {
	  myGame.move(Location.R1, Location.R2);
	  myGame.move(Location.R1, Location.R3);
	  myGame.nextTurn();
	  myGame.move(Location.R6, Location.R3);
	  myGame.move(Location.R6, Location.R2);
	  myGame.nextTurn();
	  assertFalse("Black from B_BAR must not move to Reds outer table", strategy.isValidMove(myGame, Location.B_BAR, Location.R11));

  }
  
  @Test public void movingToBearOffNotAllowedWithoutAllInInner() {
	  myGame.move(Location.R1, Location.R2);
	  myGame.move(Location.R1, Location.R3);
	  myGame.nextTurn();
	  myGame.move(Location.R6, Location.R3);
	  myGame.move(Location.R6, Location.R2);
	  myGame.nextTurn();
	  myGame.move(Location.B8, Location.B3);
	  assertFalse("Black must not move to Bear_Off without all in own inner table", strategy.isValidMove(myGame, Location.B6, Location.B_BEAR_OFF));
  }
  
}
