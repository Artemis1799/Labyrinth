package Views.SwingComponent;

import javax.swing.*;
import java.awt.*;

/**
 * The ScoreBoard class represents a graphical component that displays the scores of players in the game.
 */
public class ScoreBoard extends JPanel {
    private JPanel panel1;
    private JPanel title;
    private JPanel players;
    private JLabel red;
    private JLabel blue;
    private JLabel yellow;
    private JLabel black;


    /**
     * Constructs a new ScoreBoard instance, initializing the scoreboard layout and player scores.
     */
    public ScoreBoard() {
        title = new JPanel();
        players = new JPanel();
        this.setPreferredSize(new Dimension(200, 500));

        red = new JLabel("Red: 0");
        blue = new JLabel("Blue: 0");
        yellow = new JLabel("Yellow: 0");
        black = new JLabel("Black: 0");

        Font labelFont = new Font("Arial", Font.BOLD, 18); // Set a larger font
        red.setFont(labelFont);
        blue.setFont(labelFont);
        yellow.setFont(labelFont);
        black.setFont(labelFont);

        red.setOpaque(true);
        red.setBackground(Color.RED);
        blue.setOpaque(true);
        blue.setBackground(Color.BLUE);
        yellow.setOpaque(true);
        yellow.setBackground(Color.YELLOW);
        black.setOpaque(true);
        black.setBackground(Color.GRAY);

        title.setLayout(new GridLayout(1, 1)); // One row with one column
        JLabel titleLabel = new JLabel("Score Board");
        labelFont = new Font("Arial", Font.BOLD, 20); // Set a larger font
        titleLabel.setFont(labelFont);
        title.add(titleLabel);


        this.add(title);
        players.setLayout(new GridLayout(4, 1)); // Four rows and one column for players and scores
        players.add(red);
        players.add(blue);
        players.add(yellow);
        players.add(black);

        this.add(players);
    }

    /**
     * Updates the score of a specified player.
     *
     * @param player the name of the player
     * @param score the new score of the player
     */

    public void updateScore(String player, int score) {
        switch (player.toLowerCase()) {
            case "rouge":
                red.setText("Red: " + score);
                red.repaint();
                break;
            case "bleu":
                blue.setText("Blue: " + score);
                blue.repaint();
                break;
            case "jaune":
                yellow.setText("Yellow: " + score);
                yellow.repaint();
                break;
            case "noir":
                black.setText("Black: " + score);
                black.repaint();
                break;
        }
        players.repaint();
        this.repaint();
    }
}
