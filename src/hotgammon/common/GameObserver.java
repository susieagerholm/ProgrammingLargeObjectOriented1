package hotgammon.common;

/** A game observer is notified whenever the state changes of the
 * Game. GameObserver is the observer role of the Observer pattern. 

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
public interface GameObserver {
  /** this method is invoked whenever a checker is moved from one
   * position to another
   * @param from the position the checker had before
   * @param to the new position of the checker
   */
  public void checkerMove( Location from, Location to );
  
  /** this method is invoked whenever the dice are rolled.
   * @param values the values of the two dice
   */
  public void diceRolled( int[] values );

}
