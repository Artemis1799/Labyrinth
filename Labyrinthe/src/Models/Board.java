package Models;

import java.util.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Board class represents the game board, including tiles, objectives, and observers.
 */
public class Board {
    private Tile[][] tiles;
    private Tile additionalTile;
    private List<ObserverBoard> observers;
    private Objective[] objectiveList;

    /**
     * Constructor for the Board class. Initializes the board with tiles and objectives.
     */
    public Board() {
        this.objectiveList = generateObjectives();
        List<Objective> copyObjList = new ArrayList<>();
        shuffleObjectives(objectiveList);
        for(int i=0; i<24;i++)
        {
            copyObjList.add(objectiveList[i]);
        }

        this.tiles = new Tile[7][7];

        Random random = new Random();
        int indiceCorner=0;
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                // Fixed pieces
                if (x == 0 && y == 0 || x == 0 && y == 6 || x == 6 && y == 0 || x == 6 && y == 6) {
                    tiles[x][y] = TileFactory.createTileCorner();
                    if(indiceCorner<2) {
                        tiles[x][y].rotateTileOnRight(indiceCorner);
                    }
                    else
                    {
                        if(indiceCorner==2){
                            tiles[x][y].rotateTileOnRight(3);
                        }
                        else
                        {
                            tiles[x][y].rotateTileOnRight(2);
                        }
                    }
                    indiceCorner++;
                } else if (x % 2 == 0 && y % 2 == 0) {
                    tiles[x][y] = TileFactory.createTileT();
                    if(x==0) {
                        tiles[x][y].rotateTileOnRight(2);
                    }
                    else if(y==0) {
                        tiles[x][y].rotateTileOnRight(1);

                    }
                    else if(y==6) {
                        tiles[x][y].rotateTileOnRight(3);
                    }else if (x==2 && y==2) {
                        tiles[x][y].rotateTileOnRight(1);
                    }else if (x==2 && y==4) {
                        tiles[x][y].rotateTileOnRight(2);
                    }else if (x==4 && y==4) {
                        tiles[x][y].rotateTileOnRight(3);
                    }
                }
                // Non-fixed pieces
                else {
                    int randomTileType = random.nextInt(3);
                    switch (randomTileType) {
                        case 0:
                            tiles[x][y] = TileFactory.createTileRight();
                            break;
                        case 1:
                            tiles[x][y] = TileFactory.createTileCorner();
                            break;
                        case 2:
                            tiles[x][y] = TileFactory.createTileT();
                            break;
                    }
                    int randomTileRotation = random.nextInt(4);
                    tiles[x][y].rotateTileOnRight(randomTileRotation);
                }
            }
        }

        int anX;
        int anY;
        while(copyObjList.size()>0) {
            anX = random.nextInt(7);
            anY = random.nextInt(7);
            if (anX == 0 && anY == 0 || anX == 0 && anY == 6 || anX == 6 && anY == 0 || anX == 6 && anY == 6) {
                // Do nothing
            }
            else
            {
                if(tiles[anX][anY].getObjective()==null) {
                    tiles[anX][anY].setObjective(copyObjList.get(0));
                    copyObjList.remove(0);
                }
            }
        }
        // Generate additional tile randomly
        int randomAdditionalTileType = random.nextInt(3);
        switch (randomAdditionalTileType) {
            case 0:
                additionalTile = TileFactory.createTileRight();
                break;
            case 1:
                additionalTile = TileFactory.createTileCorner();
                break;
            case 2:
                additionalTile = TileFactory.createTileT();
                break;
        }
        this.printBoard();
    }

    /**
     * Adds observers to the board.
     *
     * @param observers The array of observers to add.
     */
    public void addObservers(ObserverBoard[] observers)
    {
        this.observers = new ArrayList<>();
        for (ObserverBoard observerBoard : observers) {
            this.observers.add(observerBoard);
        }
    }

    /**
     * Notifies observers about the range position update.
     *
     * @param players The array of players.
     */
    private void notifyRangePosition(Player players[]) {
        for (ObserverBoard observer : observers) {
            observer.updateRangePosition(this, players);
        }
    }

    /**
     * Returns the list of objectives.
     *
     * @return The list of objectives.
     */
    public Objective[] getObjectiveList() {
        return objectiveList;
    }


    /**
     * Returns the tile at the specified coordinates.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @return The tile at the specified coordinates.
     */
    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    /**
     * Returns the 2D array of tiles.
     *
     * @return The 2D array of tiles.
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Moves a tile in the specified direction.
     *
     * @param x The X coordinate of the tile to move.
     * @param y The Y coordinate of the tile to move.
     * @param direction The direction to move the tile.
     */
    public void moveTile(int x, int y, Side direction) {
        Tile temp = tiles[x][y];
        switch (direction) {
            case BOTTOM:
                if (y > 0) {
                    tiles[x][y] = tiles[x][y - 1];
                    tiles[x][y - 1] = temp;
                }
                break;
            case RIGHT:
                if (y < tiles[x].length - 1) {
                    tiles[x][y] = tiles[x][y + 1];
                    tiles[x][y + 1] = temp;
                }
                break;
            case LEFT:
                if (x > 0) {
                    tiles[x][y] = tiles[x - 1][y];
                    tiles[x - 1][y] = temp;
                }
                break;
            case TOP:
                if (x < tiles.length - 1) {
                    tiles[x][y] = tiles[x + 1][y];
                    tiles[x + 1][y] = temp;
                }
                break;
        }
    }

    /**
     * Moves a range of tiles in the specified direction.
     *
     * @param index The index of the range to move.
     * @param direction The direction to move the range.
     * @param players The array of players.
     */
    public void moveRange(int index, Side direction,Player players[]) {
    index = index-1;
        Tile temp = null;
        switch (direction) {
            case TOP:
                temp = tiles[0][index];
                for (int i = 0; i < tiles.length - 1; i++) {
                    tiles[i][index] = tiles[i + 1][index];
                }
                tiles[tiles.length - 1][index] = additionalTile;
                additionalTile = temp;
                break;
            case BOTTOM:
                temp = tiles[6][index];
                for (int i = 6; i > 0; i--) {
                    tiles[i][index] = tiles[i - 1][index];
                }
                tiles[0][index] = additionalTile;
                additionalTile = temp;
                break;
            case LEFT:
                temp = tiles[index][0];
                for (int i = 0; i < tiles[index].length - 1; i++) {
                    tiles[index][i] = tiles[index][i + 1];
                }
                tiles[index][tiles[index].length - 1] = additionalTile;
                additionalTile = temp;
                break;
            case RIGHT:
                temp = tiles[index][tiles[index].length - 1];
                for (int i = tiles[index].length - 1; i > 0; i--) {
                    tiles[index][i] = tiles[index][i - 1];
                }
                tiles[index][0] = additionalTile;
                additionalTile = temp;
                break;
        }

        notifyRangePosition(players);
    }

    /**
     * Generates a list of objectives.
     *
     * @return The generated list of objectives.
     */
    private Objective[] generateObjectives() {
        Objective[] objectives = new Objective[24];
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < 24; i++) {
            String imagePath = "ressources/Objectivs/"+i+".png";
            objectives[i] = new Objective(alphabet.substring(i, i + 1), imagePath);
        }

        return objectives;
    }

    /**
     * Shuffles the list of objectives.
     *
     * @param objectives The array of objectives to shuffle.
     */
    private void shuffleObjectives(Objective[] objectives) {
        List<Objective> objectiveList = new ArrayList<>();
        objectiveList.addAll(Arrays.asList(objectives));
        Collections.shuffle(objectiveList);
        for (int i = 0; i < objectives.length; i++) {
            objectives[i] = objectiveList.get(i);
        }
    }

    /**
     * Returns the additional tile.
     *
     * @return The additional tile.
     */
    public Tile getAdditionalTile(){
        return additionalTile;
    }

    /**
     * Prints the current state of the board.
     */
    public void printBoard() {
        int nbObj=0;
        for (int x = 0; x< 7; x++) {
            for (int y = 0; y < 7; y++) {
                Tile tile = tiles[x][y];
                if(tile.getObjective()!=null)
                {
                    nbObj++;
                }
            }
        }
    }
}
