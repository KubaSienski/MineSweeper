package Buttons;

import GameStates.Level;
import GameStates.Multi;

import java.awt.*;

import static Other.Constants.imgPlaying;

public class PlayingButton extends Button{
    private final Level level;
    private final Multi multi;
    private int index;
    private int bombs_around;
    private boolean clicked;
    private boolean flagged;

    public PlayingButton(Level level){
        super();
        this.level = level;
        multi = null;
        clicked = false;
        flagged = false;
        index = 10;
        update();
        bombs_around = 0;
    }
    public PlayingButton(Multi multi){
        super();
        this.multi = multi;
        level = null;
        clicked = false;
        flagged = false;
        index = 10;
        update();
        bombs_around = 0;
    }

    public void update(){
        img = imgPlaying[index];
        //img = imgPlaying[bombs_around];       // for testing board
    }

    public void draw(Graphics g, int x, int y){
        bounds.x = x;
        bounds.y = y;
        g.drawImage(img, x, y,32,32,null);
    }

    // mouse handling
    public void OnLeftClick() {
        if(bombs_around == 9) {
            if(level != null)
                level.setBoardLeft(-1);
            else
                multi.setBoardLeft(-1);
        }
        else if(index!=bombs_around){
            if(level != null)
                level.decreaseBoardLeft();
            else
                multi.decreaseBoardLeft();
        }
        index = bombs_around;
        clicked = true;
    }
    public void OnRightClick(){
        if (index == 10) {
            if(level != null) {
                if (level.getBombsLeft() > 0) {
                    flagged = true;
                    index = 11;
                    level.setBombsLeft(level.getBombsLeft() - 1);
                }
            }
            else
                if(multi.getBombsLeft()>0) {
                    flagged = true;
                    index = 11;
                    multi.setBombsLeft(multi.getBombsLeft() - 1);
                }

        }
        else if (index == 11) {
            flagged = false;
            index = 10;
            if(level != null)
                level.setBombsLeft(level.getBombsLeft()+1);
            else
                multi.setBombsLeft(multi.getBombsLeft()+1);
        }
    }

    public void show(){
        if(index!=bombs_around){
            if(level != null)
                level.decreaseBoardLeft();
            else
                multi.decreaseBoardLeft();
        }
        index = bombs_around;
    }

    public void reset() {
        index = 10;
        bombs_around = 0;
        clicked = false;
        flagged = false;
    }

    //  Getters and Setters

    public void setIndex(int index){
        this.index = index;
    }

    public boolean isClicked() {
        return clicked;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public int getBombs_around() {
        return bombs_around;
    }

    public void setBombs_around(int bombs_around) {
        this.bombs_around = bombs_around;
    }

    public void addBombs_around(){
        bombs_around++;
    }
}
