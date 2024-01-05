package GameStates;

import Buttons.MenuButton;
import Main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

import static Other.Constants.GAME_SIZE_MENU;

public class Menu extends State implements StateMethods{
    private final MenuButton[] buttons;

    public Menu(Game game) {
        super(game);
        buttons = new MenuButton[6];
        setButtons();
    }

    private void setButtons() {
        buttons[0] = new MenuButton(game,GAME_SIZE_MENU/2 - 125,50,254,55,0,  GameState.EASY_LVL);
        buttons[1] = new MenuButton(game,GAME_SIZE_MENU/2 - 125,110,254,55,1, GameState.MED_LVL);
        buttons[2] = new MenuButton(game,GAME_SIZE_MENU/2 - 125,170,254,55,2, GameState.HARD_LVL);
        buttons[3] = new MenuButton(game,GAME_SIZE_MENU/2 - 125,230,254,55,3, GameState.MULTI);
        buttons[4] = new MenuButton(game,GAME_SIZE_MENU/2 - 125,290,254,55,4, GameState.LEADERBOARD);
        buttons[5] = new MenuButton(game,GAME_SIZE_MENU/2 - 125,350,254,55,5, GameState.QUIT);
    }

    public void update(){
        for(MenuButton mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        for(MenuButton mb : buttons)
            mb.draw(g);
    }

    public void mouseClicked(MouseEvent e) {
        for(MenuButton mb : buttons){
            if(isIn(e, mb)) mb.OnClick(e);
        }
    }
}
