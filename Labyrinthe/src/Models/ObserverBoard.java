package Models;

import Models.*;

/**
 * The ObserverBoard interface defines a method to update the position of a range of tiles on the board.
 */
public interface ObserverBoard {

    /**
     * Updates the position of a range of tiles on the board.
     *
     * @param brd the game board
     * @param players the array of players
     */
    public void updateRangePosition(Board brd, Player players[]);
}
