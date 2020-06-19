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
    int noOfTargets;
    public WaitingListPanel(Token t) {
        super();
        noOfTargets = -1;
        this.t = t;
        this.setBackground(Color.LIGHT_GRAY);
        //repaint();
    }

    public WaitingListPanel(int noOfTargets)
    {
        super();
        this.noOfTargets = noOfTargets;
        t = null;
        this.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        String imgName = "";
        if (t != null)
        {
            if (GameMap.isTokenActive(t))
                imgName = t.getWaitingTokenImageName();
            else
                imgName = t.getGrayedWaitingTokenImageName();
        }
        else if(noOfTargets > 0)
        {
            imgName = "" + noOfTargets;
        }
        else
        {
            throw  new IllegalArgumentException();
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(GameMap.IMG_FOLDER + imgName + GameMap.IMG_EXTENSION));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int minSide = Math.min(getWidth(),getHeight());
        int sideSize = (int) (minSide * 0.95);
        g.drawImage(img, (getWidth() - sideSize)/2, (getHeight() - sideSize)/2, sideSize, sideSize,  null);

        g2d.dispose();
    }

}
