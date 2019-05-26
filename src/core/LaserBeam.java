/**
 * Laser Beam created by a Creator Token. It moves in a straight line until it hits a token. That token will determine the following state of the beam.
 */

package core;
import java.awt.Point;
import java.util.ArrayList;

public class LaserBeam {
	private Direction direction;
	private Point location;
	private ArrayList<Point> pathHistory;
	private boolean isStuck;
	
	public LaserBeam(Point location, Direction direction) {
		isStuck = false;
		this.direction = direction;
		this.location = location;
		pathHistory = new ArrayList<Point>();
	}

	//moves the LaserBeam by one cell in the current Direction
	void move()
	{
		pathHistory.add(location);
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
		//TODO This commented part should be checked in GameMap, if LaserBeam goes out of bounds
		/*try {
			t = GameMap.getShapeLocatedInXY(location.x, location.y);
			if(t != null)
			{
				//direction = t.action();
			}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Index of of bounds: " + location.x + "," + location.y);
		}*/
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}
