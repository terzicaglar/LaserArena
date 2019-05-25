/**
 * Laser Beam created by a Creator Token. It moves in a straight line until it hits a token. That token will determine the following state of the beam.
 */

package core;
import java.awt.Point;
import java.util.ArrayList;

import tokens.Token;

public class LaserBeam {
	Direction direction;
	Point location;
	ArrayList<Point> previousPath;
	Token t;
	boolean isStuck;
	
	public LaserBeam(Point location, Direction direction) {
		isStuck = false;
		this.direction = direction;
		this.location = location;
		previousPath = new ArrayList<Point>();
	}
	
	void move()
	{
		previousPath.add(location);
		switch(direction)
		{
			case EAST:
				location = new Point(location.x+1, location.y);
				break;
			case WEST:
				location = new Point(location.x-1, location.y);
				break;
			case NORTH:
				location = new Point(location.x, location.y-1);
				break;
			case SOUTH:
				location = new Point(location.x, location.y+1);
				break;
		}
		try {
			t = Map.getShapeLocatedInXY(location.x, location.y);
			if(t != null)
			{
				//direction = t.action();
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Index of of bounds: " + location.x + "," + location.y);
		}
		
	}
}
