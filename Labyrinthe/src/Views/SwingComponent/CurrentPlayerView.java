package Views.SwingComponent;

import Controlers.GameControler;
import Models.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The CurrentPlayerView class represents a graphical component that displays the current player's
 * information, including their name, current objective, and a button to proceed to the next turn.
 */
public class CurrentPlayerView extends JPanel {
    private JLabel playerName;
    private JLabel currentObjectiveTxt;
    private JLabel currentObjectiveImg;
    private JButton buttonNextTurn;
    private GameControler gameControler;

    /**
     * Constructs a new CurrentPlayerView instance with the specified game controller.
     *
     * @param gmControler the game controller
     */
    public CurrentPlayerView(GameControler gmControler) {
        super();
        gameControler = gmControler;
        buttonNextTurn = new JButton("Next Player");
        playerName = new JLabel();
        currentObjectiveImg = new JLabel();
        currentObjectiveTxt = new JLabel();
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        playerName.setFont(labelFont);
        labelFont = new Font("Arial", Font.BOLD, 13);
        currentObjectiveTxt.setFont(labelFont);
        playerName.setText("Current Player ");
        currentObjectiveTxt.setText("Objective to retrieve: ");
        playerName.setOpaque(true);
        currentObjectiveTxt.setOpaque(true);
        currentObjectiveImg.setOpaque(true);
        playerName.setBackground(Color.LIGHT_GRAY);
        currentObjectiveTxt.setBackground(Color.WHITE);
        currentObjectiveImg.setBackground(Color.WHITE);

        this.setLayout(new GridLayout(4, 1));
        this.add(playerName);
        this.add(currentObjectiveTxt);
        this.add(currentObjectiveImg);
        this.add(buttonNextTurn);

        buttonNextTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameControler.nextTurn();
            }
        });

    }

    /**
     * Updates the current player's information displayed in the view.
     *
     * @param player the current player
     */
    public void updateCurrentPlayer(Player player) {

        // Update the user interface
        String name = player.getName();
        String objective="Return to your starting square!";
        String imgLink=null;
        if(player.getCurrentObjective()!=null)
        {
        objective = player.getCurrentObjective().getName();
        imgLink = player.getCurrentObjective().getImgLink();
        }
        playerName.setText("Current Player: " + name);
        currentObjectiveTxt.setText("Objective to retrieve: " + objective);
        ImageIcon objImage = new ImageIcon(imgLink);
        Image resizedImg = objImage.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        objImage = new ImageIcon(resizedImg);
        currentObjectiveImg.setIcon(objImage); // Display the objective image
        currentObjectiveImg.repaint();
    }
}
