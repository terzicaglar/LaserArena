/**
 * JPanel where GameMap, Tokens and Laser Beam(s) are shown.
 */
package core;

import tokens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ArenaPanel extends JPanel implements MouseListener {
	int x, y, /*clickCount,*/ numberOfTokenClasses = 6, prevLocation = -1;
	GameMap map;
    Token t, prevToken;
    JPopupMenu popup;
    ArenaFrame arenaFrame;

	public ArenaPanel(ArenaFrame arenaFrame, int x, int y) {
		super();
		//clickCount = 0;
        t = GameMap.getTokenLocatedInXY(x,y);
		this.x = x;
		this.y = y;
		this.addMouseListener(this);
		this.arenaFrame = arenaFrame;
	}

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        /*Image image = null;
        try {
            image = ImageIO.read(new File("img/slash_blue_tr.png"));
            image = image.getScaledInstance(getWidth(), getHeight(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int iWidth2 = image.getWidth(this)/2;
        int iHeight2 = image.getHeight(this)/2;
        //int xImage = this.getParent().midWidth - iWidth2;
        //int yImage   = this.getParent().midHeight - iHeight2;
        g.drawImage(image,x,y,this);

         */

        //System.out.println("IsAllTokensPassed: " + GameMap.isAllTokensPassed());

        int midWidth = getWidth()/2;
        int midHeight = getHeight()/2;

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(3));

        String text;
        if( t == null)
            text = "";
        else
        {
            //t.paintToken is no longer used, t.drawTokenImage is used now
            //t.paintToken(g2d, getWidth(), getHeight());
            t.drawTokenImage(g2d, getWidth(), getHeight());
            t.paintIfLocationFixed(g2d, getWidth(), getHeight());
            text = GameMap.getTokenLocatedInXY(x,y).toIconString();
        }
        g2d.setColor(Color.BLACK);



        FontMetrics fm = g2d.getFontMetrics();
        int beamNo = 0, line_x2 = 0, line_y2 = 0, prev_line_x2 = 0, prev_line_y2 = 0;
        Direction prevDirection= null;
        Color colors[] = {Color.RED,  Color.DARK_GRAY, Color.MAGENTA, Color.CYAN, Color.BLUE, Color.GREEN};

        Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);

        for(LaserBeam beam: GameMap.getBeams())
        {
            //TODO: Refactor code below
            //Draw LaserBeam
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
                            prev_line_y2 = midHeight;
                        }
                        else if(prevDirection == Direction.WEST)
                        {
                            prev_line_x2 = getWidth();
                            prev_line_y2 = midHeight;
                        }
                        else if(prevDirection == Direction.NORTH)
                        {
                            prev_line_x2 = midWidth;
                            prev_line_y2 = getHeight();
                        }
                        else if(prevDirection == Direction.SOUTH)
                        {
                            prev_line_x2 = midWidth;
                            prev_line_y2 = 0;
                        }


                        if (pwd.getDirection() == Direction.EAST) {
                            line_x2 = getWidth();
                            line_y2 = getHeight() / 2;
                        }
                        else if(pwd.getDirection() == Direction.WEST)
                        {
                            line_x2 = 0;
                            line_y2 = midHeight;
                        }
                        else if(pwd.getDirection() == Direction.NORTH)
                        {
                            line_x2 = midWidth;
                            line_y2 = 0;
                        }
                        else if(pwd.getDirection() == Direction.SOUTH)
                        {
                            line_x2 = midWidth;
                            line_y2 = getHeight();
                        }


                        if(prev_line_x2 != -1 && prev_line_y2 != -1) //if there is a previous line, i.e., not newly created
                            g2d.drawLine(midWidth, midHeight, prev_line_x2, prev_line_y2);
                        if(line_x2 != -1 && line_y2 != -1) //if there is a current movable line, i.e., it is NOT stucked, hit or out of bounds, etc.
                            g2d.drawLine(getWidth() / 2, getHeight() / 2, line_x2, line_y2);
                    }
                    else //it is stucked, hit or out of bounds, etc.
                    {
                        prev_line_x2 = prev_line_y2 = -1;
                        if(prevDirection == Direction.EAST)
                        {
                            prev_line_x2 = 0;
                            prev_line_y2 = midHeight;
                        }
                        else if(prevDirection == Direction.WEST)
                        {
                            prev_line_x2 = getWidth();
                            prev_line_y2 = midHeight;
                        }
                        else if(prevDirection == Direction.NORTH)
                        {
                            prev_line_x2 = midWidth;
                            prev_line_y2 = getHeight();
                        }
                        else if(prevDirection == Direction.SOUTH)
                        {
                            prev_line_x2 = midWidth;
                            prev_line_y2 = 0;
                        }


                        if(prev_line_x2 != -1 && prev_line_y2 != -1) //prevDirection is stucked, hit or out of bounds
                        {
                            //draws a green half rectangle if target is hit
                            if(pwd.getDirection() == Direction.TARGET_HIT || pwd.getDirection() == Direction.MANDATORY_TARGET_HIT) {
                                g2d.setColor(Color.GREEN);
                                g2d.fillPolygon(new int[]{prev_line_x2 - midWidth / 4, prev_line_x2 + midWidth / 4, prev_line_x2 + midWidth / 4, prev_line_x2 - midWidth / 4},
                                        new int[]{prev_line_y2 - midHeight / 4, prev_line_y2 - midHeight / 4, prev_line_y2 + midHeight / 4, prev_line_y2 + midHeight / 4},
                                        4);
                            }
                            else { //draws a black half rectangle if stucked
                                g2d.setColor(Color.BLACK);
                                //g2d.drawString("X", prev_line_x2, prev_line_y2);
                                g2d.fillPolygon(new int[]{prev_line_x2 - midWidth / 4, prev_line_x2 + midWidth / 4, prev_line_x2 + midWidth / 4, prev_line_x2 - midWidth / 4},
                                        new int[]{prev_line_y2 - midHeight / 4, prev_line_y2 - midHeight / 4, prev_line_y2 + midHeight / 4, prev_line_y2 + midHeight / 4},
                                        4);
                            }
                        }



                    }
                    g2d.setColor(Color.BLACK);
                }
                prevDirection = pwd.getDirection();
            }
            beamNo++;
        }

        g2d.dispose();
    }

    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        //Left click changes the orientation of token
        if(t != null && e.getButton() == MouseEvent.BUTTON1){
            if(!t.isOrientationFixed())
            {
                t.nextOrientation();
            }
        }
        //Middle click deletes token
        else if(t != null && e.getButton() == MouseEvent.BUTTON2)
        {
            if(!t.isLocationFixed())
            {
                GameMap.removeTokenLocatedinXY(x,y);
                GameMap.addWaitingToken(t);
                prevToken = null;
                t = null;
            }

        }
        //Right click changes token
        else if(e.getButton() == MouseEvent.BUTTON3){
            if(t == null || !t.isLocationFixed())
            {
                Token newToken = null;
                //System.out.println("clickCount: " + clickCount);
                int waitingTokensSize = GameMap.getWaitingTokens().size();
                int location = -1;
//                if( waitingTokensSize !=0)
//                    location = clickCount%(waitingTokensSize);
                System.out.println("waitingTokensSize: " + waitingTokensSize);
                /*if(clickCount%(waitingTokensSize+1) == waitingTokensSize)
                    newToken = null;
                else*/

                for(int i = 0; i < waitingTokensSize; i++)
                {
                    if(GameMap.getIsWaitingTokenActive().get(i))
                    {
                        location = i;
                        break;
                    }
                }

                if(location >= 0) {
                    newToken = GameMap.getWaitingTokens().get(location);


                    if (prevToken != null)
                        GameMap.addWaitingToken(prevToken);

                    if (newToken != null) {
                        GameMap.addToken(newToken, new Point(x, y));
                        GameMap.removeWaitingToken(newToken);
                    } else {
                        GameMap.removeTokenLocatedinXY(x, y);
                    }

                    prevToken = newToken;
                    prevLocation = location;
                    t = newToken;
                    //clickCount++;
                }
            }
        }

        System.out.println("\twaiting tokens: " + GameMap.getWaitingTokens());
        System.out.println("\tis tokens active: " + GameMap.getIsWaitingTokenActive());
        repaint();
        arenaFrame.refresh();
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
