package GameStates;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface StateMethods {
    void update();
    void draw(Graphics g);
    void mouseClicked(MouseEvent e);
}
