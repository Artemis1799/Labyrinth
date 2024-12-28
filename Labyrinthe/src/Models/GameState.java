package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * The GameState class manages the state of the game, including players, the board,
 * and game-related logic such as turns, movements, and objectives.
 */
public class GameState {
    private boolean isGameOver;
    private Player[] players;
    private Player currentPlayer;
    private int numberTurn;
    private Board board;
    private List<ObserverGame> observers;
    private boolean additionalTilePlaced = false;

    private int lastMovedIndex;
    private Side lastMovedDirection;

    /**
     * Constructs a new GameState with the specified players and board.
     *
     * @param players the array of players
     * @param board the game board
     */
    public GameState(Player[] players, Board board) {
        this.players = players;
        this.isGameOver = false;
        this.numberTurn = 0;
        this.board = board;

        distributeObjectives(this.players, board.getObjectiveList());

        this.lastMovedIndex = -1;
        this.lastMovedDirection = null;
        this.currentPlayer= players[3];
    }

    /**
     * Notifies all observers about the player positions.
     */
    private void notifyPlayerPosition() {
        for (ObserverGame observer : observers) {
            observer.updatePlayerPosition(board, players);
        }
    }

    /**
     * Notifies all observers about the score.
     */
    private void notifyScore() {
        for (ObserverGame observer : observers) {
            observer.updateScore(currentPlayer);
        }
    }

    /**
     * Notifies all observers about the current player.
     */
    private void notifyCurrentPlayer() {
        for (ObserverGame observer : observers) {
            observer.updateCurrentPlayer(currentPlayer);
        }
    }

    /**
     * Adds observers to the game state.
     *
     * @param observers the array of observers to add
     */
    public void addObservers( ObserverGame[] observers)
    {
        this.observers = new ArrayList<>();
        for (ObserverGame observerGame : observers) {
            this.observers.add(observerGame);
        }
    }

    /**
     * Notifies all observers that the game is over.
     *
     * @param player the player who ended the game
     */
    private void notifyGameOver(Player player) {
        for (ObserverGame observer : observers) {
            observer.updateGameOver(player);
        }
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Gets the game board.
     *
     * @return the game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Gets the array of players.
     *
     * @return the array of players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Gets the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }


    /**
     * Sets the current player.
     *
     * @param currentPlayer the new current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Gets the current turn number.
     *
     * @return the current turn number
     */
    public int getNumberTurn() {
        return numberTurn;
    }

    /**
     * Moves to the next turn.
     */
    public void nextTurn() {
        int currentPlayerIndex = -1;
        for (int i = 0; i < players.length; i++) {
            if (players[i] == currentPlayer) {
                currentPlayerIndex = i;
                break;
            }
        }
        if (currentPlayerIndex == -1) {
            throw new IllegalStateException("Current player not found in players array.");
        }

        int nextPlayerIndex = (currentPlayerIndex + 1) % players.length;
        setCurrentPlayer(players[nextPlayerIndex]);
        numberTurn++;
        additionalTilePlaced = false;
        notifyCurrentPlayer();
    }

    /**
     * Moves a range of tiles on the board.
     *
     * @param index the index of the tile
     * @param direction the direction of the move
     */
    public void moveRangeGS(int index, Side direction) {
        if(!additionalTilePlaced && !isInverseMove(index, direction)){
            board.moveRange(index, direction, players);
            updatePlayerPositions(index, direction);
            additionalTilePlaced = true;
            lastMovedIndex = index;
            lastMovedDirection = direction;
        }
    }

    /**
     * Checks if the move is the inverse of the last move.
     *
     * @param index the index of the tile
     * @param direction the direction of the move
     * @return true if the move is the inverse of the last move, false otherwise
     */
    private boolean isInverseMove(int index, Side direction) {
        if (lastMovedIndex == index) {
            switch (lastMovedDirection) {
                case TOP:
                    return direction == Side.BOTTOM;
                case BOTTOM:
                    return direction == Side.TOP;
                case LEFT:
                    return direction == Side.RIGHT;
                case RIGHT:
                    return direction == Side.LEFT;
                default:
                    return false;
            }
        }
        return false;
    }

    /**
     * Moves a player in a specified direction.
     *
     * @param player the player to move
     * @param direction the direction of the move
     */
    public void movePlayer(Player player, Side direction) {
        if (additionalTilePlaced) {
            int newX = player.getPositionX();
            int newY = player.getPositionY();

            switch (direction) {
                case TOP:
                    newX--;
                    break;
                case BOTTOM:
                    newX++;
                    break;
                case LEFT:
                    newY--;
                    break;
                case RIGHT:
                    newY++;
                    break;
            }

            // Check if the player is trying to move out of bounds
            if (newX < 0 || newX >= board.getTiles().length || newY < 0 || newY >= board.getTiles()[0].length) {
                return;
            }

            Tile currentTile = board.getTile(player.getPositionX(), player.getPositionY());
            Tile targetTile = board.getTile(newX, newY);

            boolean canMove = false;
            switch (direction) {
                case TOP:
                    canMove = currentTile.isBorderOpen(Side.TOP) && targetTile.isBorderOpen(Side.BOTTOM);
                    break;
                case BOTTOM:
                    canMove = currentTile.isBorderOpen(Side.BOTTOM) && targetTile.isBorderOpen(Side.TOP);
                    break;
                case LEFT:
                    canMove = currentTile.isBorderOpen(Side.LEFT) && targetTile.isBorderOpen(Side.RIGHT);
                    break;
                case RIGHT:
                    canMove = currentTile.isBorderOpen(Side.RIGHT) && targetTile.isBorderOpen(Side.LEFT);
                    break;
            }

            if (canMove) {
                player.setPositionX(newX);
                player.setPositionY(newY);
            }

            // Handle objective retrieval
            int x = player.getPositionX();
            int y = player.getPositionY();
            if(board.getTile(x,y).getObjective()!=null)
            {
                if(board.getTile(x,y).getObjective().equals(player.getCurrentObjective()))
                {
                    if(player.getObjectives().size()>0)
                    {
                        player.setCurrentObjective(player.getObjectives().get(0));
                        player.getObjectives().remove(0);
                        player.setScore(player.getScore()+1);
                    }
                    else
                    {
                        player.setCurrentObjective(null);
                    }
                currentPlayer = player;
                notifyCurrentPlayer();
                notifyScore();
                }
            }
            checkGameOver(player);
            notifyPlayerPosition();
        }
    }

    /**
     * Checks if the game is over for a specific player.
     *
     * @param player the player to check
     */
    private void checkGameOver(Player player) {
        int[] startPositions = {0, 0, 0, 6, 6, 0, 6, 6};
        int playerIndex = -1;
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                playerIndex = i;
                break;
            }
        }

        if (playerIndex == -1) {
            throw new IllegalStateException("Player non trouvé");
        }

        int startX = startPositions[playerIndex * 2];
        int startY = startPositions[playerIndex * 2 + 1];

        if (player.getPositionX() == startX && player.getPositionY() == startY && player.getObjectives().isEmpty() && player.getCurrentObjective()==null) {
           notifyGameOver(player);
        }
    }

    /**
     * Updates the positions of players on a line.
     *
     * @param index the index of the line
     * @param direction the direction of the move
     */
    private void updatePlayerPositions(int index, Side direction) {
        List<Player> playersOnLine = getPlayersOnLine(index, direction);
        for (Player player : playersOnLine) {
            int newX = player.getPositionX();
            int newY = player.getPositionY();
            switch (direction) {
                case TOP:
                    newX = (newX - 1 + board.getTiles().length) % board.getTiles().length;
                    break;
                case BOTTOM:
                    newX = (newX + 1) % board.getTiles().length;
                    break;
                case LEFT:
                    newY = (newY - 1 + board.getTiles()[index].length) % board.getTiles()[index].length;
                    break;
                case RIGHT:
                    newY = (newY + 1) % board.getTiles()[index].length;
                    break;
            }
            player.setPositionX(newX);
            player.setPositionY(newY);
        }

        for (Player player : playersOnLine) {
            if (player.getPositionX() < 0 || player.getPositionX() > 6 || player.getPositionY() < 0 || player.getPositionY() > 6) {
                int oppositeX = (direction == Side.TOP || direction == Side.BOTTOM) ? (board.getTiles().length - 1 - index) : index;
                int oppositeY = (direction == Side.RIGHT || direction == Side.LEFT) ? index : (board.getTiles()[index].length - 1 - index);
                player.setPositionX(oppositeX);
                player.setPositionY(oppositeY);
            }
        }



        notifyPlayerPosition();
    }

    /**
     * Gets the players on a specific line.
     *
     * @param index the index of the line
     * @param direction the direction of the line
     * @return the list of players on the line
     */
    private List<Player> getPlayersOnLine(int index, Side direction) {
        List<Player> playersOnLine = new ArrayList<>();
        for (Player player : players) {
            int playerX = player.getPositionX();
            int playerY = player.getPositionY();
            if ((direction == Side.TOP || direction == Side.BOTTOM) && playerY == index - 1) {
                playersOnLine.add(player);
            } else if ((direction == Side.LEFT || direction == Side.RIGHT) && playerX == index - 1) {
                playersOnLine.add(player);
            }
        }
        return playersOnLine;
    }

    /**
     * Distributes objectives to players.
     *
     * @param players the array of players
     * @param objectives the array of objectives
     */
    private void distributeObjectives(Player[] players, Objective[] objectives) {
        int objectivesPerPlayer = 6;
        int totalObjectives = objectives.length;
        int totalPlayers = players.length;

        if (totalObjectives < objectivesPerPlayer * totalPlayers) {
            throw new IllegalArgumentException("Probleme sur le nombre d'objectif");
        }

        for (int i = 0; i < totalPlayers; i++) {
            List<Objective> playerObjectives = new ArrayList<>();
            for (int j = 0; j < objectivesPerPlayer; j++) {
                playerObjectives.add(objectives[i * objectivesPerPlayer + j]);
            }
            players[i].setObjectives(playerObjectives);
            players[i].setCurrentObjective(players[i].getObjectives().get(0));
            players[i].getObjectives().remove(0);
        }
    }
}
