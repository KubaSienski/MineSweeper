package Buttons;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Button {
    protected BufferedImage img;
    protected Rectangle bounds;

    public Button(){
        bounds = new Rectangle(0,0,32,32);
    }
    public Button(int xPos, int yPos, int w, int h){
        bounds = new Rectangle(xPos, yPos, w,h);
    }
    public Button(BufferedImage img, int xPos, int yPos, int w, int h){
        this.img = img;
        bounds = new Rectangle(xPos, yPos, w,h);
    }

    public void draw(Graphics g){
        g.drawImage(img, bounds.x, bounds.y, bounds.width, bounds.height, null);
    }

    public void OnClick(MouseEvent e){
    }
    public Rectangle getBounds(){
        return bounds;
    }
}
