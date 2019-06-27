/**
 * JPanel where GameMap, Tokens and Laser Beam(s) are shown.
 */
package core;

import tokens.Token;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ArenaPanel extends JPanel {
	int x, y;
	GameMap map;
	public ArenaPanel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}



	@Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        
        Graphics2D g2d = (Graphics2D) g.create();
        String text;
        Token t = GameMap.getTokenLocatedInXY(x,y);
        if( t == null)
            text = "";
        else
            text = GameMap.getTokenLocatedInXY(x,y).toIconString();
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(text, x, y);
        g2d.drawString(text, x, y);
        g2d.dispose();

        /*Image image = null;
        try {
            image = ImageIO.read(new File("img/slash_blue.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int iWidth2 = image.getWidth(this)/2;
        int iHeight2 = image.getHeight(this)/2;
        //int xImage = this.getParent().getWidth()/2 - iWidth2;
        //int yImage   = this.getParent().getHeight()/2 - iHeight2;
        g.drawImage(image,x,y,this);*/


    }
}
