package Other;

import Buttons.PlayingButton;
import GameStates.GameState;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class Constants {
    public static boolean GAME_WON = false;
    public static String PLAYER_NICK = "nick";

    private static final int TILES_AMOUNT_PLAYING = 13;
    private static final int TILES_AMOUNT_MENU = 6;

    public static final BufferedImage wholeImgPlaying = loadWholeImg("/tiles.jpg");
    public static final BufferedImage[] imgPlaying = loadImgPlaying();
    public static final BufferedImage wholeImgMenu = loadWholeImg("/MenuButtons.jpg");
    public static final BufferedImage[] imgMenu = loadImgMenu();
    public static final BufferedImage BACKGROUND = loadWholeImg("/background.jpg");
    public static final BufferedImage returnImage = loadWholeImg("/returnButton.png");

    public static final int BOMBS_EASY = 10;
    public static final int SIZE_EASY = 8;
    public static final int BOMBS_MED = 40;
    public static final int SIZE_MED = 16;
    public static final int BOMBS_HARD = 99;
    public static final int SIZE_HARD = 22;

    public static final int GAME_SIZE_MENU = 500;
    public static final int GAME_SIZE_EZ = SIZE_EASY*32;
    public static final int GAME_SIZE_MED = SIZE_MED*32;
    public static final int GAME_SIZE_HARD = SIZE_HARD*32;


    private static BufferedImage loadWholeImg(String NAME) {
        BufferedImage tmp = null;
        InputStream is = PlayingButton.class.getResourceAsStream(NAME);
        try {
            tmp = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return tmp;
    }
    private static BufferedImage[] loadImgPlaying(){
        BufferedImage[] tmp = new BufferedImage[Constants.TILES_AMOUNT_PLAYING];
        for(int i = 0; i< Constants.TILES_AMOUNT_PLAYING; i++){
            tmp[i] = Constants.wholeImgPlaying.getSubimage(i*32,0,32,32);
        }
        return tmp;
    }
    private static BufferedImage[] loadImgMenu(){
        BufferedImage[] tmp = new BufferedImage[Constants.TILES_AMOUNT_MENU];
        for(int i = 0; i< Constants.TILES_AMOUNT_MENU; i++){
            tmp[i] = Constants.wholeImgMenu.getSubimage(0,i*55,254,55);
        }
        return tmp;
    }

    public static final File FILE = new File("src/Other/Leaderboard.txt");;
    public static final String[][] names = new String[3][3];
    public static final int[][] scores = new int[3][3];
    public static void Save(int time, GameState state) {
        int index = 0;
        switch (state) {
            case EASY_LVL -> {}
            case MED_LVL -> index = 1;
            case HARD_LVL -> index = 2;
        }
        if (time < scores[index][0] || scores[index][0] == 0) {
            scores[index][2] = scores[index][1];
            scores[index][1] = scores[index][0];
            scores[index][0] = time;
            names[index][2] = names[index][1];
            names[index][1] = names[index][0];
            names[index][0] = PLAYER_NICK;
        } else if (time < scores[index][1] || scores[index][1] == 0) {
            scores[index][2] = scores[index][1];
            scores[index][1] = time;
            names[index][2] = names[index][1];
            names[index][1] = PLAYER_NICK;
        } else if (time < scores[index][2] || scores[index][2] == 0) {
            scores[index][2] = time;
            names[index][2] = PLAYER_NICK;
        }
        FileWriter writer;
        try {
            writer = new FileWriter(FILE);
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    writer.write(names[i][j] + "\n");
                    writer.write(scores[i][j] + "\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
