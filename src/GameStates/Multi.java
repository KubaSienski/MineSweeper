package GameStates;

import Buttons.Button;
import Buttons.PlayingButton;
import Main.Game;
import Other.TimeCounter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

import static Other.Constants.*;

public class Multi extends State implements StateMethods{
    private final PlayingButton[][] playingButtons;
    private final Button returnButton;
    private final int BOMBS = BOMBS_MED;
    private final int BOARD_SIZE = SIZE_MED;
    private int bombsLeft;
    private int boardLeft;
    private boolean gameOver = false;
    private boolean isStarted = false;
    private final TimeCounter timeCounter = new TimeCounter();
    private int time1, time2;
    private boolean firstGameLost = false;
    private boolean secondGameLost = false;
    private boolean firstGame = true;

    public Multi(Game game) {
        super(game);
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

    @Override
    public void update() {
        if(!gameOver && isStarted)
            timeCounter.update();
        for(PlayingButton[] mb : playingButtons)
            for (PlayingButton b : mb)
                b.update();
    }

    @Override
    public void draw(Graphics g) {
        returnButton.draw(g);
        g.setColor(Color.WHITE);
        g.fillRect(BOARD_SIZE*16-50,0,100,50);
        g.fillRect(BOARD_SIZE*32-105,0,100,50);
        g.setColor(Color.BLACK);
        String text1, text2;
        if(firstGame) {
            text1 = "w trakcie";
            text2 = "-";
        }
        else if(firstGameLost){
            text1 = "przegrana";
            text2 = "w trakcie";
        }
        else{
            text1 = String.valueOf(time1);
            text2 = "w trakcie";
        }
        g.drawString("Gracz1: " + text1,BOARD_SIZE*16-49, 20);
        g.drawString("Gracz2: " + text2,BOARD_SIZE*16-49, 40);
        g.drawString("BOMBS LEFT: "+ bombsLeft, BOARD_SIZE*32-104, 20);
        g.drawString("TIME: " + timeCounter.getTimeInSec(), BOARD_SIZE*32-104, 40);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                playingButtons[i][j].draw(g, j * 32, i * 32 + 50);
            }
        }
        if(gameOver){
            g.setColor(Color.WHITE);
            g.fillRect(BOARD_SIZE*32/2 - 100,BOARD_SIZE*32/2 - 100,200,120);
            g.setColor(Color.BLACK);
            g.drawString("KONIEC GRY", BOARD_SIZE*32/2 - 50, BOARD_SIZE*32/2 - 80);
            if(!firstGameLost && !secondGameLost){
                if(time1 < time2) {
                    g.drawString("Wygrywa Gracz1!", BOARD_SIZE * 32 / 2 - 50, BOARD_SIZE * 32 / 2 - 60);
                    g.drawString("Czas: " + time1 + " sekund", BOARD_SIZE * 32 / 2 - 50, BOARD_SIZE * 32 / 2 - 40);
                }
                else if(time1 > time2){
                    g.drawString("Wygrywa Gracz2!", BOARD_SIZE * 32 / 2 - 50, BOARD_SIZE * 32 / 2 - 60);
                    g.drawString("Czas: " + time2 + " sekund", BOARD_SIZE * 32 / 2 - 50, BOARD_SIZE * 32 / 2 - 40);
                }
                else
                    g.drawString("Remis", BOARD_SIZE*32/2 - 50, BOARD_SIZE*32/2 - 60);
            }
            else if(!firstGameLost && secondGameLost){
                g.drawString("Wygrywa Gracz1!", BOARD_SIZE*32/2 - 50, BOARD_SIZE*32/2 - 60);
                g.drawString("Czas: " + time1 + " sekund", BOARD_SIZE * 32 / 2 - 50, BOARD_SIZE * 32 / 2 - 40);
            }
            else if(firstGameLost && !secondGameLost){
                g.drawString("Wygrywa Gracz2!", BOARD_SIZE*32/2 - 50, BOARD_SIZE*32/2 - 60);
                g.drawString("Czas: " + time2 + " sekund", BOARD_SIZE * 32 / 2 - 50, BOARD_SIZE * 32 / 2 - 40);
            }
            else{
                g.drawString("Gracze przegrywają!", BOARD_SIZE*32/2 - 50, BOARD_SIZE*32/2 - 60);
            }
        }
    }

    @Override
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
        firstGameLost = false;
        firstGame = true;
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
    public void resetBoard(){
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
        if(firstGame){
            time1 = timeCounter.getTimeInSec();
            firstGame = false;
            firstGameLost = false;
            resetBoard();
        }
        else {
            time2 = timeCounter.getTimeInSec();
            secondGameLost = false;
            bombsLeft = 0;
            for (PlayingButton[] pb_tab : playingButtons) {
                for (PlayingButton pb : pb_tab) {
                    if (pb.getBombs_around() == 9) {
                        pb.setIndex(11);
                    }
                }
            }
            gameOver = true;
        }
    }

    private void GameOver() {
        if(firstGame){
            resetBoard();
            time1 = timeCounter.getTimeInSec();
            firstGame = false;
            firstGameLost = true;
        }
        else {
            secondGameLost = true;
            gameOver = true;
            for (PlayingButton[] pb_tab : playingButtons) {
                for (PlayingButton pb : pb_tab) {
                    if (pb.getBombs_around() == 9)
                        pb.OnLeftClick();
                }
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
}
