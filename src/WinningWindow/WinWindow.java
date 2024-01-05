package WinningWindow;

import GameStates.GameState;
import GameStates.Level;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Other.Constants.*;

public class WinWindow extends JFrame implements ActionListener {
    private final GameState state;
    private final int time;
    private final JButton button;
    private final JTextField textField;
    private final JLabel label1;
    private final Level level;

    public WinWindow(Level level, GameState state, int time) {
        setSize(200,120);
        setLayout(null);
        setUndecorated(true);

        this.level = level;
        this.state = state;
        this.time = time;

        label1 = new JLabel("Wygrana!");
        label1.setBounds(70,10,180, 30);
        add(label1);

        textField = new JTextField("Podaj nick");
        textField.setBounds(25, 40, 150, 30);
        add(textField);

        button = new JButton("OK");
        button.setBounds(70, 80, 60, 30);
        button.addActionListener(this);
        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        GAME_WON = false;
        PLAYER_NICK = textField.getText();
        Save(time, state);
        setVisible(false);
        GameState.state = GameState.MENU;
        level.reset();
        level.getGame().update();
    }
}