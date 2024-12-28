package Views;

import Models.ObserverBoard;
import Models.ObserverGame;
import Models.Player;
import Views.SwingComponent.*;
import java.awt.*;
import javax.swing.*;

/**
 * The InterfaceSwing class represents the graphical user interface for the game,
 * implementing the ObserverBoard and ObserverGame interfaces to update the game state.
 */
public class InterfaceSwing extends JFrame implements ObserverBoard, ObserverGame {

    private Board TheBoard;
    private CurrentPlayerView TheCurrentPlayer;
    private MovementArrows TheArrow;
    private CurrentTile TheCurrentTile;
    private ScoreBoard TheScoreBoard;
    private JPanel TheData;
    private JLabel RoundNumber;
    private JPanel TheGame;

    /**
     * Constructs a new InterfaceSwing instance, initializing the JFrame.
     */
    public InterfaceSwing() {
        // JFrame initialization
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize); // Window size will be equal to screen size

    }

    /**
     * Sets the components of the interface.
     *
     * @param TheBoard the game board
     * @param TheArrow the movement arrows
     * @param TheData the data panel
     * @param TheCurrentPlayer the current player view
     * @param TheScoreBoard the score board
     * @param TheCurrentTile the current tile view
     */
    public void setComponent(Board TheBoard,MovementArrows TheArrow,JPanel TheData,CurrentPlayerView TheCurrentPlayer,ScoreBoard TheScoreBoard, CurrentTile TheCurrentTile)
    {
        this.TheCurrentPlayer=TheCurrentPlayer;
        this.TheBoard = TheBoard;
        this.TheScoreBoard=TheScoreBoard;
        //this.TheGame = TheGame;
        this.TheCurrentTile = TheCurrentTile;
        this.TheData=TheData;
        this.TheArrow = TheArrow;
        TheCurrentTile.repaint();
        TheBoard.setPreferredSize(new Dimension(900, 900));
        TheScoreBoard.setPreferredSize(new Dimension(200, 400));
        TheCurrentTile.setPreferredSize(new Dimension(200, 400));

        // Configure TheData to display the lap number
        RoundNumber = new JLabel("The Labyrinth");
        RoundNumber.setFont(new Font("Arial", Font.BOLD, 24));
        TheData.setLayout(new FlowLayout(FlowLayout.CENTER));
        TheData.add(RoundNumber);

        // Creation of a panel for the Western region
        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.add(TheScoreBoard, BorderLayout.NORTH);
        westPanel.add(TheCurrentTile, BorderLayout.SOUTH);

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(TheCurrentPlayer, BorderLayout.NORTH);
        eastPanel.add(TheArrow, BorderLayout.SOUTH);

        // JFrame main layout
        this.setLayout(new BorderLayout());

        // Adding panels to the JFrame
        this.add(TheBoard, BorderLayout.CENTER);
        this.add(TheData, BorderLayout.NORTH);
        this.add(westPanel, BorderLayout.WEST);
        this.add(eastPanel, BorderLayout.EAST);

        // Window display
        //this.RepaintMain();
        this.pack();
        this.setVisible(true);
    }

    /**
     * Updates the position of a range of tiles on the board.
     *
     * @param brd the game board
     * @param players the array of players
     */
    public void updateRangePosition(Models.Board brd, Player players[])
    {
        TileSwing cur = TheBoard.updateBoard(brd, players);
        TheCurrentTile.setCurrentTilePanel(cur);
    }

    /**
     * Repaints the main components of the interface.
     */
    public void RepaintMain()
    {
        TheBoard.repaint();
        TheBoard.revalidate();
        TheGame.repaint();
        TheGame.revalidate();
    }

    /**
     * Updates the positions of players on the board.
     *
     * @param brd the game board
     * @param players the array of players
     */
    public void updatePlayerPosition(Models.Board brd, Player players[])
    {
        TheBoard.updateBoard(brd, players);
    }

    /**
     * Updates the score of a player.
     *
     * @param player the player whose score is being updated
     */
    public void updateScore(Player player)
    {
        String playerName = player.getName();
        int score = player.getScore();
        TheScoreBoard.updateScore(playerName,score);
    }

    /**
     * Updates the current player.
     *
     * @param player the current player
     */
    public void updateCurrentPlayer(Player player)
    {
        TheCurrentPlayer.updateCurrentPlayer(player);
        TheArrow.setCurrentPlayer(player);
    }

    /**
     * Updates the game over status for a player.
     *
     * @param pla the player for whom the game is over
     */
    public void updateGameOver(Player pla) {
        this.getContentPane().removeAll();
        JLabel label = new JLabel(pla.getName() + " has won");
        label.setForeground(Color.GREEN);
        Font font = new Font("Arial", Font.BOLD, 100);
        label.setFont(font);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0)); // Window displayClose application when button is clicked
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        panel.add(quitButton, BorderLayout.SOUTH);
        this.getContentPane().add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

}
