package core;

import tokens.Token;

import javax.swing.*;
import java.awt.*;

class WaitingListPanel extends JPanel {
    private Token t;
    public WaitingListPanel(Token t) {
        super();
        this.t = t;
        //repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        t.drawWaitingTokenImage(g2d, getWidth(), getHeight());

        g2d.dispose();
    }

}
