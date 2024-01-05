package Main;

import javax.swing.*;

public class GameWindow extends JFrame {

    public GameWindow(GamePanel gamePanel) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gamePanel);
        setResizable(false);
        pack();
        setVisible(true);
    }

    public void update(GamePanel gamePanel){
        add(gamePanel);
        pack();
        setVisible(true);
    }
}
