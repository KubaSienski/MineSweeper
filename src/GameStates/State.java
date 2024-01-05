package GameStates;

import Buttons.Button;
import Main.Game;

import java.awt.event.MouseEvent;

public abstract class State {
    protected Game game;

    public State(Game game){
        this.game = game;
    }

    public boolean isIn(MouseEvent e, Button b){
        return b.getBounds().contains(e.getX(),e.getY());
    }
}
