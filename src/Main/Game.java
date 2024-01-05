package Main;

import GameStates.*;
import GameStates.Menu;

import java.awt.*;

public class Game{
    private final GameWindow gameWindow;
    private final GamePanel gamePanel = new GamePanel(this, GameState.MENU);
    private Menu menu;
    private Level Ez, Med, Hard;
    private Multi multi;
    private Leaderboard leaderboard;

    public Game() {
        initClasses();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        gamePanel.requestFocusInWindow();
    }

    private void initClasses() {
        menu = new Menu(this);
        Ez = new Level(this, GameState.EASY_LVL);
        Med = new Level(this, GameState.MED_LVL);
        Hard = new Level(this, GameState.HARD_LVL);
        multi = new Multi(this);
        leaderboard = new Leaderboard(this);
    }

    public void update(){
        switch (GameState.state) {
            case MENU -> {
                menu.update();
                gamePanel.setPanelSize(GameState.MENU);
            }
            case EASY_LVL -> {
                Ez.update();
                gamePanel.setPanelSize(GameState.EASY_LVL);
            }
            case MED_LVL -> {
                Med.update();
                gamePanel.setPanelSize(GameState.MED_LVL);
            }
            case HARD_LVL -> {
                Hard.update();
                gamePanel.setPanelSize(GameState.HARD_LVL);
            }
            case MULTI -> {
                multi.update();
                gamePanel.setPanelSize(GameState.MED_LVL);
            }
        }
        gameWindow.update(gamePanel);
    }

    public void render(Graphics g) {
        switch (GameState.state) {
            case MENU -> menu.draw(g);
            case EASY_LVL -> Ez.draw(g);
            case MED_LVL -> Med.draw(g);
            case HARD_LVL -> Hard.draw(g);
            case LEADERBOARD -> leaderboard.draw(g);
            case MULTI -> multi.draw(g);
            case QUIT -> System.exit(0);
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Level getEz() {
        return Ez;
    }

    public Level getMed() {
        return Med;
    }

    public Level getHard() {
        return Hard;
    }

    public Multi getMulti(){
        return multi;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }
}
