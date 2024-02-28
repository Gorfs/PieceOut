package src.gui.gamePlay;

import javax.swing.*;

import src.gui.UI.*;
import src.logic.PieceLogic;
import src.config.*;
import src.piece.Pieces;
import src.utils.Debug;


public class GamePanel extends JPanel {
    /**
     * This Panel is the main panel that is the player sees when completing the puzzle
     * Contains the arena, the pieces, the UI,etc..
     */
    private ArenaPanel arenaPanel = new ArenaPanel();
    private JPanel topPanel = new TopPanel();
    private JPanel bottomPanel = new BottomPanel();

    public ArenaPanel getArenaPanel(){
        return this.arenaPanel;
    }

    public GamePanel(){
        super();
        this.setLayout(null);
        this.setBackground(Colours.BACKGROUND);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(topPanel);
        //centerArenaPanel.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, Color.RED));
        this.add(arenaPanel);
        this.add(bottomPanel);

    }



    public void update(){
        // updating the arena like a tick in the gameloop


        //redrawing pieces
        for(Pieces piece: PieceLogic.getPieces()){
            GuiPiece guiPiece = new GuiPiece(piece);
            guiPiece.paintComponent(getGraphics());
        }
        // this.revalidate();
        // this.repaint();
    }






}
