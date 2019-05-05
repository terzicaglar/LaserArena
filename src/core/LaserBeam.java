package core;
import java.awt.Point;
import java.util.ArrayList;

import shapes.Shape;

public class LaserBeam {
	Direction direction;
	Point location;
	ArrayList<Point> previousPath;
	Map m;
	Shape s;
	boolean isStuck;
	
	public LaserBeam(Map m, Point location, Direction direction) {
		isStuck = false;
		this.direction = direction;
		this.location = location;
		previousPath = new ArrayList<Point>();
		this.m = m;
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
			s = m.getShapeLocatedInXY(location.x, location.y);
			if(s != null)
			{
				direction = s.action();
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Index of of bounds: " + location.x + "," + location.y);
		}
		
	}
}
