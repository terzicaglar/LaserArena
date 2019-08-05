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
        int beamNo = 0, line_x2 = 0, line_y2 = 0, prev_line_x2 = 0, prev_line_y2 = 0;
        Direction prevDirection= null;
        Color colors[] = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.CYAN};
        for(LaserBeam beam: GameMap.getBeams())
        {
            //TODO: Refactor code below
            for(PointWithDirection pwd: beam.getPathHistory())
            {
                if(x == (int)pwd.getPoint().getX() && y == (int)pwd.getPoint().getY())
                {
                    //text += " " + beamNo + pwd.getDirection().toString().substring(0,2);
                    g2d.setColor(colors[beamNo]);
                    prev_line_x2 = prev_line_y2 = line_y2 = line_x2 = -1;
                    //System.out.println(pwd.getDirection());
                    if(pwd.getDirection().isMovable()) {

                        if(prevDirection == Direction.EAST)
                        {
                            prev_line_x2 = 0;
                            prev_line_y2 = getHeight()/2;
                        }
                        else if(prevDirection == Direction.WEST)
                        {
                            prev_line_x2 = getWidth();
                            prev_line_y2 = getHeight()/2;
                        }
                        else if(prevDirection == Direction.NORTH)
                        {
                            prev_line_x2 = getWidth()/2;
                            prev_line_y2 = getHeight();
                        }
                        else if(prevDirection == Direction.SOUTH)
                        {
                            prev_line_x2 = getWidth()/2;
                            prev_line_y2 = 0;
                        }


                        if (pwd.getDirection() == Direction.EAST) {
                            line_x2 = getWidth();
                            line_y2 = getHeight() / 2;
                        }
                        else if(pwd.getDirection() == Direction.WEST)
                        {
                            line_x2 = 0;
                            line_y2 = getHeight()/2;
                        }
                        else if(pwd.getDirection() == Direction.NORTH)
                        {
                            line_x2 = getWidth()/2;
                            line_y2 = 0;
                        }
                        else if(pwd.getDirection() == Direction.SOUTH)
                        {
                            line_x2 = getWidth()/2;
                            line_y2 = getHeight();
                        }


                        if(prev_line_x2 != -1 && prev_line_y2 != -1) //if there is a previous line, i.e., not newly created
                            g2d.drawLine(getWidth()/2, getHeight()/2, prev_line_x2, prev_line_y2);
                        if(line_x2 != -1 && line_y2 != -1) //if there is a current movable line, i.e., it is NOT stucked, hit or out of bounds, etc.
                            g2d.drawLine(getWidth() / 2, getHeight() / 2, line_x2, line_y2);
                    }
                    else //it is stucked, hit or out of bounds, etc.
                    {
                        prev_line_x2 = prev_line_y2 = -1;
                        if(prevDirection == Direction.EAST)
                        {
                            prev_line_x2 = 0;
                            prev_line_y2 = getHeight()/2;
                        }
                        else if(prevDirection == Direction.WEST)
                        {
                            prev_line_x2 = getWidth();
                            prev_line_y2 = getHeight()/2;
                        }
                        else if(prevDirection == Direction.NORTH)
                        {
                            prev_line_x2 = getWidth()/2;
                            prev_line_y2 = getHeight();
                        }
                        else if(prevDirection == Direction.SOUTH)
                        {
                            prev_line_x2 = getWidth()/2;
                            prev_line_y2 = 0;
                        }
                        if(prev_line_x2 != -1 && prev_line_y2 != -1) //if there is a current movable line, i.e., it is NOT stucked, hit or out of bounds, etc.
                            g2d.drawString("X", prev_line_x2, prev_line_y2);

                    }
                    g2d.setColor(Color.BLACK);
                }
                prevDirection = pwd.getDirection();
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
