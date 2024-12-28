package Views.SwingComponent;

import Models.Tile;

import javax.swing.*;
import java.awt.*;

/**
 * The CurrentTile class represents a graphical component that displays the current tile
 * and provides a button to rotate it.
 */
public class CurrentTile extends JPanel {
    private JLabel currentTileTxt;
    private TileSwing currentTilePanel;
    private JButton panelRotate;

    /**
     * Constructs a new CurrentTile instance with the specified tile.
     *
     * @param tile the tile to display
     */
    public CurrentTile(Tile tile)
    {
        super();
        //currentTilePanel = new TileSwing(tile);
        currentTilePanel = new TileSwing();
        currentTileTxt = new JLabel("Tile to use:");
        panelRotate=new JButton();
        panelRotate.setText("Rotate Tile");
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        labelFont = new Font("Arial", Font.BOLD, 13);
        panelRotate.setFont(labelFont);
        panelRotate.setOpaque(true);
        panelRotate.setBackground(Color.WHITE);
        panelRotate.addActionListener(e -> {
            Tile tileToRotate = currentTilePanel.getTile();
            tileToRotate.rotateTileOnRight(1);
            setCurrentTilePanel(new TileSwing(tileToRotate));
            currentTilePanel.revalidate();
        });
        currentTileTxt.setFont(labelFont);
        currentTileTxt.setOpaque(true);
        currentTileTxt.setBackground(Color.WHITE);
        this.setLayout(new GridLayout(3,1));
        this.add(currentTileTxt);
        this.add(currentTilePanel);
        this.add(panelRotate);

    }

    /**
     * Sets the current tile panel.
     *
     * @param pnl the new tile panel to set
     */
    public void setCurrentTilePanel(TileSwing pnl)
    {
        if (currentTilePanel != null) {
            this.remove(currentTilePanel);
        }

        // Update the current component
        currentTilePanel = pnl;

        // Add the new component
        this.add(currentTilePanel);
    }

    /**
     * Gets the current tile panel.
     *
     * @return the current tile panel
     */
    public TileSwing getCurrentTilePanel() {
        return currentTilePanel;
    }

}
