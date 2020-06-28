package core;

import tokens.Token;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static core.Constants.IMG_EXTENSION;
import static core.Constants.IMG_FOLDER;

//TODO: to be deleted if not used
class WaitingListPanel extends JPanel {
    private Token t;
    int noOfTargets;

    public WaitingListPanel(Token t) {
        super();
        noOfTargets = -1;
        this.t = t;
        //this.setBackground(Color.LIGHT_GRAY);
        //repaint();
    }

    public WaitingListPanel(int noOfTargets) {
        super();
        this.noOfTargets = noOfTargets;
        t = null;
        //this.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int noOfTargetsDisplayed = noOfTargets - (GameMap.getInstance().getNoOfRandomTargetsHit() + GameMap.getInstance().getNoOfMandatoryTargetsHit());
        if (noOfTargetsDisplayed < 0)
            noOfTargetsDisplayed = 0;
        String imgName = "";
        if (t != null) {
            if (GameMap.getInstance().isTokenActive(t))
                imgName = t.getWaitingTokenImageName();
            else
                imgName = t.getGrayedWaitingTokenImageName();
        } else if (noOfTargetsDisplayed == 0 && GameMap.getInstance().isLevelFinished()) {
            imgName = "0_green";
        } else if (noOfTargetsDisplayed >= 0) {
            imgName = "" + noOfTargetsDisplayed;
        } else {
            throw new IllegalArgumentException();
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(IMG_FOLDER + imgName + IMG_EXTENSION));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int minSide = Math.min(getWidth(), getHeight());
        int sideSize = (int) (minSide * 0.95);
        g.drawImage(img, (getWidth() - sideSize) / 2, (getHeight() - sideSize) / 2, sideSize, sideSize, null);
    }

}
