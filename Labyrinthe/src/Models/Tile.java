package Models;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

/**
 * The Tile class represents a tile in a game or graphical application.
 * Each tile can have open or closed borders on its four sides and can contain an image and an objective.
 */
public class Tile {
        private Boolean topBorder;
        private Boolean bottomBorder;
        private Boolean rightBorder;
        private Boolean leftBorder;
        private int numberOfRotation;
        private String img;
        private Objective item;

        /**
         * Constructor for the Tile class.
         *
         * @param top State of the top border.
         * @param bottom State of the bottom border.
         * @param right State of the right border.
         * @param left State of the left border.
         * @param imgPath Path to the tile's image.
         */
        public Tile(boolean top, boolean bottom, boolean right, boolean left, String imgPath) {
                this.topBorder = top;
                this.bottomBorder = bottom;
                this.rightBorder = right;
                this.leftBorder = left;
                this.numberOfRotation=0;
                // Charger l'image
                        img = imgPath;
        }

        /**
         * Checks if a specific border is open.
         *
         * @param border The border to check.
         * @return true if the border is open, false otherwise.
         */
        public Boolean isBorderOpen(Side border) {
                switch (border) {
                        case RIGHT:
                                return rightBorder;
                        case LEFT:
                                return leftBorder;
                        case TOP:
                                return topBorder;
                        case BOTTOM:
                                return bottomBorder;
                        default:
                                return false;
                }
        }

        /**
         * Returns the path to the tile's image.
         *
         * @return The path to the image.
         */
        public String getTileImg() {
                return img;
        }

        /**
         * Sets the path to the tile's image.
         *
         * @param newImgPath The new path to the image.
         */
        public void setTileImg(String newImgPath) {
                img=newImgPath;
        }

        /**
         * Sets the objective of the tile.
         *
         * @param anItem The objective to set.
         */
        public void setObjective(Objective anItem) {
                item = anItem;
        }

        /**
         * Returns the objective of the tile.
         *
         * @return The objective of the tile.
         */
        public Objective getObjective() {
                return item;
        }

        /**
         * Sets the state of a specific border.
         *
         * @param border The border to set.
         * @param state The state of the border (open or closed).
         */
        public void setBorder(Side border, Boolean state) {
                switch (border) {
                        case RIGHT:
                                rightBorder = state;
                                break;
                        case LEFT:
                                leftBorder = state;
                                break;
                        case TOP:
                                topBorder = state;
                                break;
                        case BOTTOM:
                                bottomBorder = state;
                                break;
                        default:
                                break;
                }
        }

        /**
         * Rotates the tile to the right a certain number of times.
         *
         * @param nb The number of rotations to perform.
         */
        public void rotateTileOnRight(int nb) {
                for (int i = 0; i < nb; i++) {
                        // Faire tourner les frontières
                        boolean temp = this.topBorder;
                        this.topBorder = this.leftBorder;
                        this.leftBorder = this.bottomBorder;
                        this.bottomBorder = this.rightBorder;
                        this.rightBorder = temp;
                        this.numberOfRotation=(numberOfRotation+1)%4;
                        // Faire tourner l'image
                }
        }

        /**
         * Returns the number of rotations performed on the tile.
         *
         * @return The number of rotations.
         */
        public int getNumberOfRotation() {
                return numberOfRotation;
        }
}
