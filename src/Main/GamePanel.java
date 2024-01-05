package Main;

import GameStates.GameState;
import Inputs.MouseHandler;

import javax.swing.*;
import java.awt.*;

import static Other.Constants.*;


public class GamePanel extends JPanel {
    MouseHandler mouseHandler = new MouseHandler(this);
    GameState state;
    Game game;

    public GamePanel(Game game, GameState state){
        this.game = game;
        this.state = state;
        setPanelSize(state);
        addMouseListener(mouseHandler);

    }

    public void update(){
        game.update();
    }

    public void setPanelSize(GameState state) {
        Dimension size;
        switch (state) {
            case EASY_LVL -> size = new Dimension(GAME_SIZE_EZ, GAME_SIZE_EZ+50);
            case MED_LVL -> size = new Dimension(GAME_SIZE_MED, GAME_SIZE_MED+50);
            case HARD_LVL -> size = new Dimension(GAME_SIZE_HARD, GAME_SIZE_HARD+50);
            default -> size = new Dimension(GAME_SIZE_MENU, GAME_SIZE_MENU);
        }

        setPreferredSize(size);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(BACKGROUND,0,0,null);
        game.render(g);
    }

    public Game getGame(){
        return game;
    }
}
