package Models;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The TileFactory class provides methods to create different types of Tile objects
 * and to load images from file paths.
 */
public class TileFactory {

    /**
     * Default constructor for the TileFactory class.
     */
    public TileFactory(){}

    /**
     * Creates a Tile with the right border open.
     *
     * @return A new Tile object with the right border open.
     */
    public static Tile createTileRight() {
        return new Tile(false, false, true, true, "ressources/rightTile.png");
    }

    /**
     * Creates a Tile with the bottom and right borders open (corner tile).
     *
     * @return A new Tile object with the bottom and right borders open.
     */
    public static Tile createTileCorner() {
        return new Tile(false, true, true, false, "ressources/cornerTile.png");
    }

    /**
     * Creates a Tile with the top, right, and left borders open (T-shaped tile).
     *
     * @return A new Tile object with the top, right, and left borders open.
     */
    public static Tile createTileT() {
        return new Tile(true, false, true, true, "ressources/tTile.png");
    }

    /**
     * Loads an image from the specified file path.
     *
     * @param path The path to the image file.
     * @return The loaded BufferedImage, or null if the image could not be loaded.
     */
    public static BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de l'image : " + path);
            e.printStackTrace();
        }
        return image;
    }
}