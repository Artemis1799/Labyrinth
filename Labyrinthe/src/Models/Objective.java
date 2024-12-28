package Models;

/**
 * The Objective class represents an objective in a game, including its name and image link.
 */
public class Objective {
    private String name;
    private String imgLink;

    /**
     * Constructor for the Objective class.
     *
     * @param name The name of the objective.
     * @param img The link to the objective's image.
     */
    public Objective(String name, String img) {
        this.name = name;
        this.imgLink = img;
    }

    /**
     * Returns the name of the objective.
     *
     * @return The name of the objective.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the objective.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the link to the objective's image.
     *
     * @return The link to the image.
     */
    public String getImgLink() {
        return imgLink;
    }

    /**
     * Sets the link to the objective's image.
     *
     * @param imgLink The link to the image to set.
     */
    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}