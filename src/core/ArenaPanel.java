/**
 * JPanel where GameMap, Tokens and Laser Beam(s) are shown.
 */
package core;

import tokens.Token;

import javax.swing.*;
import java.awt.*;

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
        int beamNo = 0;
        for(LaserBeam beam: GameMap.getBeams())
        {
            for(PointWithDirection pwd: beam.getPathHistory())
            {
                if(x == (int)pwd.getPoint().getX() && y == (int)pwd.getPoint().getY())
                {
                    text += "X" + beamNo + pwd.getDirection().toString().substring(0,2);
                }

            }
            beamNo++;
        }

        int xText = (getWidth() - fm.stringWidth(text)) / 2;
        int yText = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(text, xText, yText);
        g2d.drawString(text, xText, yText);

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

        g2d.dispose();
    }
}
