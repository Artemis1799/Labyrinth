package Views.SwingComponent;

import Controlers.GameControler;
import Models.GameState;
import Models.Player;
import Models.Side;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The MovementArrows class represents a graphical component that provides arrow buttons
 * for moving the current player in different directions.
 */
public class MovementArrows extends JPanel {
        private GameControler gmControler;
        private Player currentPlayer;

        /**
         * Constructs a new MovementArrows instance with the specified game controller.
         *
         * @param gmControler the game controller
         */
        public MovementArrows(GameControler gmControler) {
                this.gmControler = gmControler;
                // Create a JPanel with a GridBagLayout to position the buttons
                JPanel panel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5); // Margins between components
                Dimension buttonSize = new Dimension(80, 80);

                // Create the buttons
                JButton upButton = new JButton("↑");
                upButton.setPreferredSize(buttonSize);

                JButton leftButton = new JButton("←");
                leftButton.setPreferredSize(buttonSize);

                JButton downButton = new JButton("↓");
                downButton.setPreferredSize(buttonSize);

                JButton rightButton = new JButton("→");
                rightButton.setPreferredSize(buttonSize);

                // Add action listeners to move the player
                upButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                moveCurrentPlayer(Side.TOP);
                        }
                });

                leftButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                moveCurrentPlayer(Side.LEFT);
                        }
                });

                downButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                moveCurrentPlayer(Side.BOTTOM);
                        }
                });

                rightButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                moveCurrentPlayer(Side.RIGHT);
                        }
                });

                // Position the buttons in the GridBagLayout
                gbc.gridx = 1;
                gbc.gridy = 0;
                panel.add(upButton, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                panel.add(leftButton, gbc);

                gbc.gridx = 1;
                gbc.gridy = 1;
                panel.add(downButton, gbc);

                gbc.gridx = 2;
                gbc.gridy = 1;
                panel.add(rightButton, gbc);

                this.add(panel);
        }

        /**
         * Sets the current player.
         *
         * @param player the current player
         */
        public void setCurrentPlayer(Player player)
        {
                this.currentPlayer = player;
        }

        /**
         * Moves the current player in the specified direction.
         *
         * @param direction the direction of the move
         */
        private void moveCurrentPlayer(Side direction) {
                if (currentPlayer != null) {
                        gmControler.makeMove(currentPlayer, direction);
                }
        }
}
