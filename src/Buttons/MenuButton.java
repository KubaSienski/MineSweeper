package Buttons;

import GameStates.GameState;
import Main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;

import static Other.Constants.imgMenu;

public class MenuButton extends Button{
    GameState state;
    private final int index;
    Game game;

    public MenuButton(Game game, int x, int y, int w, int h, int index, GameState state) {
        super(x,y,w,h);
        this.index = index;
        this.game = game;
        update();
        this.state = state;
    }

    public void update(){
        img = imgMenu[index];
    }

    public void draw(Graphics g){
        g.drawImage(img, bounds.x, bounds.y, 254, 55,null);
    }

    public void OnClick(MouseEvent e) {
        GameState.state = state;
    }
}
