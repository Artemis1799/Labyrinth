package Views.SwingComponent;

import Models.Player;
import Models.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The TileSwing class represents a graphical tile component in the game,
 * displaying the tile image, player pawns, and objectives.
 */
public class TileSwing extends JPanel {
    private String imageLink;
    private BufferedImage imageBuffered;
    private JLabel tileLabel;
    private Tile tile;
    private JLabel playerLabel;
    private JLabel tileImageLabel;
    private ImageIcon tileImage;
    private JLabel objLabel;

    /**
     * Constructs a new TileSwing instance with default settings.
     */
    public TileSwing() {
    }

    /**
     * Constructs a new TileSwing instance with the specified tile and players.
     *
     * @param tile the tile to display
     * @param players the array of players
     */
    public TileSwing(Tile tile, Player[] players) {
        super();
        this.tile = tile;
        this.setLayout(new BorderLayout());
        imageLink = tile.getTileImg();
        // Load the tile image
        try {
            imageBuffered = ImageIO.read(new File(imageLink));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading tile image: " + imageLink);
        }

        // Apply necessary rotations
        for (int i = 0; i < tile.getNumberOfRotation(); i++) {
            imageBuffered = rotateImage(imageBuffered);
        }


        // Resize the tile image
        tileImage = new ImageIcon(imageBuffered);
        Image resizedImg = tileImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        tileImage = new ImageIcon(resizedImg);

        // Configure the main panel
        this.setPreferredSize(new Dimension(100, 100));
        tileImageLabel = new JLabel(tileImage);
        tileImageLabel.setBounds(0, 0, 100, 100); // Position the tile image


        // Add player pawns
        if (players != null) {
            int[][] positions = {
                    {15, 15}, {45, 15}, // Positions of the first two players
                    {15, 45}, {45, 40}  // Positions of the last two players
            };

            for (int i = 0; i < players.length; i++) {
                if (players[i] != null) {
                    try {
                        BufferedImage pawnImage = ImageIO.read(new File(players[i].getImgLink()));
                        Image scaledPawnImage = pawnImage.getScaledInstance(300, 150, Image.SCALE_SMOOTH);
                        playerLabel = new JLabel(new ImageIcon(scaledPawnImage));
                        playerLabel.setBounds(positions[i][0], positions[i][1], 50, 50);
                        this.add(playerLabel);
                        this.repaint();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println("Error loading player image: " + players[i].getImgLink());
                    }
                }
            }
        }

        // Add objective image
        String objectiveLink = null;
        if(tile.getObjective()!=null)
        {
            objectiveLink = tile.getObjective().getImgLink();
            BufferedImage imageBufferedObj=null;
            try {
                imageBufferedObj = ImageIO.read(new File(objectiveLink));
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading tile image: " + objectiveLink);
            }
            Image scaledObjImage = imageBufferedObj.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            objLabel = new JLabel(new ImageIcon(scaledObjImage));
            objLabel.setBounds(30, 30, 50, 50);
            this.add(objLabel);
        }
        this.add(tileImageLabel, BorderLayout.CENTER);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Constructs a new TileSwing instance with the specified tile.
     *
     * @param tile the tile to display
     */
    public TileSwing(Tile tile)
    {
        this.tile = tile;
        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(100, 100));
        imageLink = tile.getTileImg();
        // Load the tile image
        try {
            imageBuffered = ImageIO.read(new File(imageLink));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading tile image: " + imageLink);
        }

        // Apply necessary rotations
        for (int i = 0; i < tile.getNumberOfRotation(); i++) {
            imageBuffered = rotateImage(imageBuffered);
        }


        // Resize the tile image
        ImageIcon tileImage = new ImageIcon(imageBuffered);
        Image resizedImg = tileImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        tileImage = new ImageIcon(resizedImg);

        // Configure the main panel
        JLabel tileImageLabel = new JLabel(tileImage);
        tileImageLabel.setBounds(0, 0, 100, 100); // Position the tile image

        // Add objective image
        String objectiveLink = null;
        if(tile.getObjective()!=null)
        {
            objectiveLink = tile.getObjective().getImgLink();
            BufferedImage imageBufferedObj=null;
            try {
                imageBufferedObj = ImageIO.read(new File(objectiveLink));
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error loading objective image: " + objectiveLink);
            }
            Image scaledObjImage = imageBufferedObj.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JLabel objLabel = new JLabel(new ImageIcon(scaledObjImage));
            objLabel.setBounds(30, 30, 50, 50);
            this.add(objLabel);
        }
        this.add(tileImageLabel,BorderLayout.CENTER);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    /**
     * Rotates the given image by 90 degrees.
     *
     * @param img the image to rotate
     * @return the rotated image
     */
    public BufferedImage rotateImage(BufferedImage img) {
        if (img != null) {
            int width = img.getWidth();
            int height = img.getHeight();
            BufferedImage rotatedImg = new BufferedImage(height, width, img.getType());

            // Use AffineTransform to apply the rotation
            AffineTransform transform = new AffineTransform();
            transform.translate(height / 2.0, width / 2.0);
            transform.rotate(Math.toRadians(90));
            transform.translate(-width / 2.0, -height / 2.0);

            AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
            op.filter(img, rotatedImg);

            return rotatedImg; // Replace the image with the rotated version

        }
        return img;
    }

    /**
     * Gets the tile associated with this component.
     *
     * @return the tile
     */
    public Tile getTile(){return this.tile;}
}
