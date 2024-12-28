import Controlers.GameControler;
import Controlers.TileControler;
import Models.GameState;
import Models.Player;
import Views.InterfaceSwing;
import Models.ObserverBoard;
import Models.ObserverGame;
import Views.SwingComponent.*;

import javax.swing.*;

/**
 * The Main class contains the entry point for the application.
 * It initializes the game components and starts the game.
 */
public class Main {

    /**
     * The main entry point for the application.
     * This method initializes and starts the game by setting up the game state,
     * controllers, and the graphical user interface.
     *
     * @param args the command-line arguments (not used in this application)
     */
    public static void main(String[] args) {

        // Initialize the main interface
        InterfaceSwing tmp = new InterfaceSwing();

        // Initialize observers
        ObserverBoard obsBoa[] = new ObserverBoard[1];
        obsBoa[0]=tmp;
        ObserverGame obsGame[] = new ObserverGame[1];
        obsGame[0]=tmp;

        // Initialize the game board
        Models.Board gameBoard = new Models.Board();
        gameBoard.addObservers(obsBoa);

        // Initialize players
        Player[] players=new Player[4];
        players[0]=new Player("Rouge",0,0, "ressources/playerRed.png");
        players[1]=new Player("Bleu",0,6, "ressources/playerBlue.png");
        players[2]=new Player("Noir",6,0, "ressources/playerBlack.png");
        players[3]=new Player("Jaune",6,6, "ressources/playerYellow.png");

        // Initialize the game state
        GameState gmState = new GameState(players, gameBoard);
        gmState.addObservers(obsGame);

        // Initialize controllers
        TileControler tlCtrl=new TileControler();
        GameControler gmControler= new GameControler(gmState);

        // Initialize the graphical components
        Views.SwingComponent.Board TheBoard = new Board(tmp, tlCtrl);
        Views.SwingComponent.MovementArrows TheArrow = new MovementArrows(gmControler);
        JPanel TheData = new JPanel();
        Views.SwingComponent.CurrentPlayerView TheCurrentPlayer = new CurrentPlayerView(gmControler);
        Views.SwingComponent.CurrentTile TheCurrentTile = new CurrentTile(gameBoard.getAdditionalTile());
        Views.SwingComponent.ScoreBoard TheScoreBoard = new ScoreBoard();

        // Set the current tile panel
        TheCurrentTile.setCurrentTilePanel(new TileSwing(gameBoard.getAdditionalTile()));

        // Set the components in the main interface
        tmp.setComponent(TheBoard, TheArrow, TheData, TheCurrentPlayer, TheScoreBoard, TheCurrentTile);

        // Set the game state in the tile controller
        tlCtrl.setGameState(gmState);

        // Start the game
        gmState.nextTurn();
        tmp.updatePlayerPosition(gmState.getBoard(), gmState.getPlayers());
    }
}