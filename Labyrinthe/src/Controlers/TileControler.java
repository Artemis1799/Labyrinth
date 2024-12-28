package Controlers;

import Models.*;

/**
 * The TileControler class manages tile-related operations within the game, such as rotating tiles
 * and moving ranges of tiles on the board.
 */
public class TileControler {
    private GameState gameState;

    /**
     * Rotates a tile a specified number of times to the right.
     *
     * @param tile the tile to rotate
     * @param times the number of times to rotate the tile
     */
    public void rotateTile(Tile tile, int times)
    {
        tile.rotateTileOnRight(times);
    }

    /**
     * Sets the game state for the tile controller.
     *
     * @param gms the game state to set
     */
    public void setGameState(GameState gms){
        this.gameState = gms;
    }

    /**
     * Moves a range of tiles on the board in a specified direction.
     *
     * @param index the index of the tile range
     * @param direction the direction of the move
     */
    public void moveRangeOnBoard(int index, Side direction) {
        gameState.moveRangeGS(index, direction);
    }
}
