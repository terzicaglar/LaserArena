package core;

import tokens.Token;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        String imgName = "";
        if(GameMap.isTokenActive(t))
            imgName = t.getWaitingTokenImageName();
        else
            imgName = t.getGrayedWaitingTokenImageName();

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(GameMap.IMG_FOLDER + imgName + GameMap.IMG_TYPE));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);

        g2d.dispose();
    }

}
