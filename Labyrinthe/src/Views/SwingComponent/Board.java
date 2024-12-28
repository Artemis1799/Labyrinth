package Views.SwingComponent;

import Controlers.TileControler;
import Models.Player;
import Models.Side;
import Models.Tile;
import Views.InterfaceSwing;

import java.awt.*;
import javax.swing.*;

import static Models.Side.*;

/**
 * The Board class represents a graphical component that displays the game board,
 * including tiles and player positions. It allows for updating the board and handling
 * tile movements.
 */
public class Board extends JPanel {
    private JPanel mainFrame;
    private TileControler tlCtrl;
    private TileSwing[][] panels = new TileSwing[9][9]; // Tableau de JPanel pour représenter la grille
    private InterfaceSwing parentInterface;  // Référence à l'interface Swing principale

    /**
     * Constructs a new Board instance with the specified parent interface and tile controller.
     *
     * @param parent the main Swing interface
     * @param aControler the tile controller
     */
    public Board(InterfaceSwing parent, TileControler aControler) {
        super();
        this.parentInterface = parent;
        this.tlCtrl = aControler;
        this.setLayout(new GridLayout(9, 9));
        this.setPreferredSize(new Dimension(900, 900)); // 9x9 grid of 100x100 panels
    }

    /**
     * Updates the board with the specified game board and players.
     *
     * @param brd the game board
     * @param players the array of players
     * @return the additional tile as a TileSwing component
     */
    public TileSwing updateBoard(Models.Board brd, Player players[])
    {
        this.removeAll();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if ((i == 0 || i == 8) && (j == 0 || j == 8)) {
                    panels[i][j] = new TileSwing();
                    this.add(panels[i][j]);
                } else if (((i == 0 || i == 8) ^ (j == 0 || j == 8)) && !(i % 2 != 0 || j % 2 != 0)) {
                    JPanel buttonPanel = new JPanel(new GridBagLayout());
                    JButton button = new JButton("Insert");
                    button.setPreferredSize(new Dimension(90, 50));
                    Side direction = null;
                    int loc = -1;
                    if (i == 0) { // Top of the grid
                        direction = BOTTOM;
                        loc = j;
                    } else if (i == 8) { // Bottom of the grid
                        direction = TOP;
                        loc = j;
                    } else if (j == 0) { // Left of the grid
                        direction = RIGHT;
                        loc = i;
                    } else if (j == 8) { // Right of the grid
                        direction = LEFT;
                        loc = i;
                    }
                    button.putClientProperty("direction", direction);
                    button.putClientProperty("loc", loc);
                    button.addActionListener(e -> {
                        Side btnDirection = (Side) button.getClientProperty("direction");
                        int btnLoc = (int) button.getClientProperty("loc");
                        tlCtrl.moveRangeOnBoard(btnLoc, btnDirection);
                        this.repaint();
                        //parentInterface.RepaintMain();
                    });
                    buttonPanel.add(button);
                    this.add(buttonPanel);
                } else if (!(i == 0 || i == 8) ^ (j == 0 || j == 8)) {
                    Tile tile = (brd.getTile(i-1,j-1));
                    Boolean playerAreHere = false;
                    Player[] playersThatAreHere = new Player[4];
                    for(int l=0; l<4; l++)
                    {
                        if(players[l].getPositionX()==i-1 && players[l].getPositionY()==j-1)
                        {
                            playersThatAreHere[l]=players[l];
                            playerAreHere=true;
                        }
                        else
                        {
                            playersThatAreHere[l]=null;
                        }
                    }
                    if(!playerAreHere) {
                        panels[i][j] = new TileSwing(tile);
                    }
                    else
                    {
                        panels[i][j] = new TileSwing(tile,playersThatAreHere);
                    }
                    this.add(panels[i][j]);

                } else {
                    panels[i][j] = new TileSwing();
                    this.add(panels[i][j]);
                }
            }
        }
        this.revalidate();
        this.repaint();
        return new TileSwing(brd.getAdditionalTile());
    }
}
