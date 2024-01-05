package GameStates;

import Buttons.Button;
import Main.Game;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static Other.Constants.*;

public class Leaderboard extends State implements StateMethods {
    private final Button returnButton;
    private final String[] TEXTS = {"Łatwy", "Średni", "Trudny"};

    public Leaderboard(Game game) {
        super(game);
        returnButton = new Buttons.Button(returnImage, 5, 0, 117, 49);
        try {
            Scanner scanner = new Scanner(FILE);
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++) {
                    names[i][j] = scanner.nextLine();
                    scores[i][j] = Integer.parseInt(scanner.nextLine());
                }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        }
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
        returnButton.draw(g);
        g.setColor(Color.WHITE);
        g.fillRect(100, 100, 300, 300);
        g.setColor(Color.BLACK);
        for (int i = 0; i < 3; i++) {
            g.drawString(TEXTS[i], 110, 110 + i * 80);
            for (int j = 0; j < 3; j++) {
                if(scores[i][j] == 0){
                    g.drawString(j+1 + ". --", 120, 130 + i * 80 + j * 20);
                    continue;
                }
                g.drawString(j+1 + ". " + names[i][j] + " - " + scores[i][j] + " sekund", 120, 130 + i * 80 + j * 20);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isIn(e, returnButton)) GameState.state = GameState.MENU;
    }
}
