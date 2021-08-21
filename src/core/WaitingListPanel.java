package core;

import tokens.Token;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static core.Constants.IMG_EXTENSION;
import static core.Constants.IMG_FOLDER;

//TODO: to be deleted if not used
class WaitingListPanel extends JPanel implements MouseListener {
    private Token t;
    private int noOfTargets;
    private GameMap map;

    public WaitingListPanel(Token t) {
        super();
        map = GameMap.getInstance();
        noOfTargets = -1;
        this.t = t;
        this.addMouseListener(this);
        //this.setBackground(Color.LIGHT_GRAY);
        //repaint();
    }

    public WaitingListPanel(int noOfTargets) {
        super();
        map = GameMap.getInstance();
        this.noOfTargets = noOfTargets;
        t = null;
        this.addMouseListener(this);
        //this.setBackground(Color.LIGHT_GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int noOfTargetsDisplayed = noOfTargets - (map.getNoOfRandomTargetsHit() + map.getNoOfMandatoryTargetsHit());
        if (noOfTargetsDisplayed < 0)
            noOfTargetsDisplayed = 0;
        String imgName = "";
        if (t != null) {
            if (map.isTokenActive(t))
                imgName = t.getWaitingTokenImageName();
            else
                imgName = t.getGrayedWaitingTokenImageName();
        } else if (noOfTargetsDisplayed == 0 && map.isLevelFinished()) {
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

        if(t != null && map.getSelectedWaitingToken() == t)
            this.setBorder(BorderFactory.createDashedBorder(Color.BLACK));
        else
            this.setBorder(null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(t != null && e.getButton() == MouseEvent.BUTTON1){
            if (map.isTokenActive(t)) {
                if (map.getSelectedWaitingToken() == t) {
                    map.setSelectedWaitingToken(null);
                } else {
                    map.setSelectedWaitingToken(t);
                }
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
