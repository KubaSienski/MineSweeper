package GameStates;

import Buttons.Button;
import Buttons.PlayingButton;
import Main.Game;
import Other.TimeCounter;
import WinningWindow.WinWindow;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

import static Other.Constants.*;

public class Level extends State implements StateMethods {
    private final GameState state;
    private final PlayingButton[][] playingButtons;
    private final Button returnButton;
    private int BOMBS;
    private int BOARD_SIZE;
    private int bombsLeft;
    private int boardLeft;
    private boolean gameOver = false;
    private boolean isStarted = false;
    private final TimeCounter timeCounter = new TimeCounter();

    public Level(Game game, GameState state) {
        super(game);
        this.state = state;
        GetSizeAndBombAmount();
        playingButtons = new PlayingButton[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                playingButtons[i][j] = new PlayingButton(this);
            }
        }
        AddBombs();
        setBombsAround();
        returnButton = new Buttons.Button(returnImage,5,0,117,49);
        boardLeft = BOARD_SIZE * BOARD_SIZE - BOMBS;
        bombsLeft = BOMBS;
    }

    // Resizing depending on difficulty
    private void GetSizeAndBombAmount() {
        if (state == GameState.EASY_LVL) {
            BOMBS = BOMBS_EASY;
            BOARD_SIZE = SIZE_EASY;
        } else if (state == GameState.MED_LVL || state == GameState.MULTI) {
            BOMBS = BOMBS_MED;
            BOARD_SIZE = SIZE_MED;
        } else if (state == GameState.HARD_LVL) {
            BOMBS = BOMBS_HARD;
            BOARD_SIZE = SIZE_HARD;
        }
    }

    private void AddBombs() {
        int bombs_placed = 0;
        while (bombs_placed < BOMBS) {
            int index1 = ThreadLocalRandom.current().nextInt(0, BOARD_SIZE);
            int index2 = ThreadLocalRandom.current().nextInt(0, BOARD_SIZE);
            if (playingButtons[index1][index2].getBombs_around() != 9) {
                playingButtons[index1][index2].setBombs_around(9);
                bombs_placed++;
            }
        }
    }

    // Setting digits around bombs
    public void setBombsAround() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (playingButtons[i][j].getBombs_around() == 9) { continue; }

                if (i > 0)
                    if (playingButtons[i - 1][j].getBombs_around() == 9)
                        playingButtons[i][j].addBombs_around();
                if (j > 0)
                    if (playingButtons[i][j - 1].getBombs_around() == 9)
                        playingButtons[i][j].addBombs_around();
                if (i < BOARD_SIZE - 1)
                    if (playingButtons[i + 1][j].getBombs_around() == 9)
                        playingButtons[i][j].addBombs_around();
                if (j < BOARD_SIZE - 1)
                    if (playingButtons[i][j + 1].getBombs_around() == 9)
                        playingButtons[i][j].addBombs_around();
                if (i > 0 && j > 0)
                    if (playingButtons[i - 1][j - 1].getBombs_around() == 9)
                        playingButtons[i][j].addBombs_around();
                if (i > 0 && j < BOARD_SIZE - 1)
                    if (playingButtons[i - 1][j + 1].getBombs_around() == 9)
                        playingButtons[i][j].addBombs_around();
                if (j > 0 && i < BOARD_SIZE - 1)
                    if (playingButtons[i + 1][j - 1].getBombs_around() == 9)
                        playingButtons[i][j].addBombs_around();
                if (i < BOARD_SIZE - 1 && j < BOARD_SIZE - 1)
                    if (playingButtons[i + 1][j + 1].getBombs_around() == 9)
                        playingButtons[i][j].addBombs_around();
            }
        }
    }

    public void update(){
        if(!gameOver && isStarted)
            timeCounter.update();
        for(PlayingButton[] mb : playingButtons)
            for (PlayingButton b : mb)
                b.update();
    }

    // drawing board
    public void draw(Graphics g) {
        returnButton.draw(g);
        g.setColor(Color.WHITE);
        g.fillRect(BOARD_SIZE*32-105,0,100,50);
        g.setColor(Color.BLACK);
        g.drawString("BOMBS LEFT: "+ bombsLeft, BOARD_SIZE*32-104, 20);
        g.drawString("TIME: " + timeCounter.getTimeInSec(), BOARD_SIZE*32-104, 40);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                playingButtons[i][j].draw(g, j * 32, i * 32 + 50);
            }
        }
    }

    // on clicked
    public void mouseClicked(MouseEvent e) {
        if(isIn(e,returnButton)) {
            reset();
            GameState.state = GameState.MENU;
            return;
        }
        if(gameOver) return;
        boolean clicked = false;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (isIn(e, playingButtons[i][j])) {
                    if (!isStarted){
                        timeCounter.Start();
                        isStarted = true;
                    }
                    if (e.getButton() == MouseEvent.BUTTON1){
                        if(!playingButtons[i][j].isClicked() && !playingButtons[i][j].isFlagged())
                            showAround(i, j);
                        if(boardLeft == 0){
                            GameWon();
                        }
                        if(boardLeft == -1){
                            GameOver();
                            playingButtons[i][j].setIndex(12);
                        }
                    }
                    else if(e.getButton() == MouseEvent.BUTTON3) {
                        playingButtons[i][j].OnRightClick();
                    }
                    clicked = true;
                    break;
                }
            }
            if(clicked)break;
        }
    }

    public void reset(){
        gameOver = false;
        isStarted = false;
        timeCounter.reset();
        for (PlayingButton[] pb_tab : playingButtons){
            for(PlayingButton pb : pb_tab) {
                pb.reset();
            }
        }
        AddBombs();
        setBombsAround();
        boardLeft = BOARD_SIZE * BOARD_SIZE - BOMBS;
        bombsLeft = BOMBS;
    }

    private void GameWon(){
        gameOver = true;
        bombsLeft = 0;
        for(PlayingButton[] pb_tab : playingButtons){
            for(PlayingButton pb : pb_tab){
                if(pb.getBombs_around() == 9) {
                    pb.setIndex(11);
                }
            }
        }
        int winningTime = timeCounter.getTimeInSec();
        GAME_WON = true;
        WinWindow winWindow = new WinWindow(this, state, winningTime);
        winWindow.setVisible(true);
    }

    private void GameOver() {
        gameOver = true;
        for(PlayingButton[] pb_tab : playingButtons){
            for(PlayingButton pb : pb_tab){
                if(pb.getBombs_around() == 9)
                    pb.OnLeftClick();
            }
        }
    }

    private void showAround(int i, int j){
        if(playingButtons[i][j].isClicked()) return;
        playingButtons[i][j].OnLeftClick();
        if(playingButtons[i][j].getBombs_around()!=0) return;

        if(i>0) {
            playingButtons[i-1][j].show();
            if(playingButtons[i-1][j].getBombs_around() == 0)
                showAround(i-1,j);
        }
        if(j>0) {
            playingButtons[i][j-1].show();
            if(playingButtons[i][j-1].getBombs_around() == 0)
                showAround(i,j-1);
        }
        if(i<BOARD_SIZE-1) {
            playingButtons[i+1][j].show();
            if(playingButtons[i+1][j].getBombs_around() == 0)
                showAround(i+1,j);
        }
        if(j<BOARD_SIZE-1) {
            playingButtons[i][j+1].show();
            if(playingButtons[i][j+1].getBombs_around() == 0)
                showAround(i,j+1);
        }
        if (i>0 && j>0){
            playingButtons[i-1][j-1].show();
            if(playingButtons[i-1][j-1].getBombs_around() == 0)
                showAround(i-1,j-1);
        }
        if (i<BOARD_SIZE-1 && j>0){
            playingButtons[i+1][j-1].show();
            if(playingButtons[i+1][j-1].getBombs_around() == 0)
                showAround(i+1,j-1);
        }
        if (j<BOARD_SIZE-1 && i>0){
            playingButtons[i-1][j+1].show();
            if(playingButtons[i-1][j+1].getBombs_around() == 0)
                showAround(i-1,j+1);
        }
        if (i<BOARD_SIZE-1 && j<BOARD_SIZE-1){
            playingButtons[i+1][j+1].show();
            if(playingButtons[i+1][j+1].getBombs_around() == 0)
                showAround(i+1,j+1);
        }
    }

    //  Getters and Setters

    public int getBombsLeft() {
        return bombsLeft;
    }

    public void setBombsLeft(int bombsLeft) {
        this.bombsLeft = bombsLeft;
    }

    public void setBoardLeft(int boardLeft) {
        this.boardLeft = boardLeft;
    }

    public void decreaseBoardLeft() {
        --boardLeft;
    }

    public Game getGame(){
        return game;
    }
}
