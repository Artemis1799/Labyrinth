package Controlers;

import Models.GameState;
import Models.Player;
import Models.Side;

/**
 * The GameControler class manages game-related operations, such as making moves, advancing turns,
 * and checking the game over status.
 */
public class GameControler {

    private GameState gameState;

    /**
     * Constructs a new GameControler with the specified game state.
     *
     * @param gm the game state
     */
    public GameControler(GameState gm)
    {
        gameState = gm;
    }

    /**
     * Makes a move for the current player in a specified direction.
     *
     * @param currentPlayer the current player
     * @param direction the direction of the move
     */
    public void makeMove(Player currentPlayer, Side direction)
    {
        gameState.movePlayer(currentPlayer,direction);
    }

    /**
     * Advances to the next turn in the game.
     */
    public void nextTurn()
    {
        gameState.nextTurn();
    }

    /**
     * Checks if the game is over.
     *
     * @return false (This method is currently a placeholder and always returns false)
     */
    public Boolean checkGameOver()
    {
        return false;
    }
}
