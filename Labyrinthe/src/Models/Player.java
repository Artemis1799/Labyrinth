package Models;

import java.util.List;

/**
 * The Player class represents a player in a game, including their name, image, objectives, position, and score.
 */
public class Player {
    private String name;
    private String imgLink;
    private Objective currentObjective;
    private int positionX;
    private int positionY;
    private List<Objective> objectives;
    private int score;

    /**
     * Constructor for the Player class.
     *
     * @param name The name of the player.
     * @param positionX The initial X position of the player.
     * @param positionY The initial Y position of the player.
     * @param image The link to the player's image.
     */
    public Player(String name, int positionX, int positionY, String image) {
        this.name = name;
        this.positionX = positionX;
        this.positionY = positionY;
        this.imgLink=image;
    }

    /**
     * Returns the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the current objective of the player.
     *
     * @return The current objective.
     */
    public Objective getCurrentObjective() {
        return currentObjective;
    }

    /**
     * Sets the current objective of the player.
     *
     * @param currentObjective The objective to set.
     */
    public void setCurrentObjective(Objective currentObjective) {
        this.currentObjective = currentObjective;
    }

    /**
     * Returns the X position of the player.
     *
     * @return The X position.
     */
    public int getPositionX() {
        return positionX;
    }

    /**
     * Sets the X position of the player.
     *
     * @param positionX The X position to set.
     */
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    /**
     * Returns the Y position of the player.
     *
     * @return The Y position.
     */
    public int getPositionY() {
        return positionY;
    }

    /**
     * Sets the Y position of the player.
     *
     * @param positionY The Y position to set.
     */
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    /**
     * Sets the list of objectives for the player.
     *
     * @param objectives The list of objectives to set.
     */
    public void setObjectives(List<Objective> objectives) {
        this.objectives = objectives;
    }

    /**
     * Returns the list of objectives for the player.
     *
     * @return The list of objectives.
     */
    public List<Objective> getObjectives() {
        return objectives;
    }

    /**
     * Returns the score of the player.
     *
     * @return The score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the score of the player.
     *
     * @param score The score to set.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Moves the player in the specified direction.
     *
     * @param side The direction to move the player.
     */
    public void movePlayer(Side side) {
        switch (side) {
            case TOP:
                positionY--;
                break;
            case BOTTOM:
                positionY++;
                break;
            case LEFT:
                positionX--;
                break;
            case RIGHT:
                positionX++;
                break;
        }
    }

    /**
     * Returns the link to the player's image.
     *
     * @return The link to the image.
     */
    public String getImgLink(){return this.imgLink;}
}
