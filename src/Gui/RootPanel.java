package src.Gui;

import javax.swing.*;
import java.awt.*;

import src.Gui.GamePlay.*;

    public class RootPanel extends JPanel{

        private static CardLayout layout = new CardLayout();
        private static ArenaPanel arenaPanel = new ArenaPanel();
        
        public RootPanel(){
            super();
            this.setLayout(layout);
            this.add(arenaPanel, "arena");
            this.setBackground(Color.BLACK);

            layout.show(this, "arena");
            
        }
    }