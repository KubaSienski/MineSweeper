package Inputs;

import GameStates.GameState;
import Main.GamePanel;

import java.awt.event.MouseEvent;

import static Other.Constants.GAME_WON;

public class MouseHandler implements java.awt.event.MouseListener {
private final GamePanel gamePanel;
public MouseHandler(GamePanel gamePanel){
this.gamePanel = gamePanel;
}

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        switch (GameState.state){
            case MENU -> gamePanel.getGame().getMenu().mouseClicked(e);
            case MED_LVL -> gamePanel.getGame().getMed().mouseClicked(e);
            case HARD_LVL -> gamePanel.getGame().getHard().mouseClicked(e);
            case EASY_LVL -> gamePanel.getGame().getEz().mouseClicked(e);
            case MULTI -> gamePanel.getGame().getMulti().mouseClicked(e);
            case LEADERBOARD -> gamePanel.getGame().getLeaderboard().mouseClicked(e);
            case QUIT -> System.exit(0);
        }
        if(!GAME_WON) {
            gamePanel.repaint();
            gamePanel.update();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
