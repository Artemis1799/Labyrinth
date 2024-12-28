package Models;

import Models.*;

/**
 * The ObserverGame interface defines methods to update various aspects of the game state,
 * including player positions, scores, the current player, and the game over status.
 */
public interface ObserverGame {

        /**
         * Updates the positions of players on the board.
         *
         * @param brd the game board
         * @param players the array of players
         */
        public void updatePlayerPosition(Board brd, Player players[]);

        /**
         * Updates the score of a player.
         *
         * @param player the player whose score is being updated
         */
        public void updateScore(Player player);

        /**
         * Updates the current player.
         *
         * @param player the current player
         */
        public void updateCurrentPlayer(Player player);

        /**
         * Updates the game over status for a player.
         *
         * @param player the player for whom the game is over
         */
        public void updateGameOver(Player player);
}
