/**
 * Laser Beam created by a Creator Token. It moves in a straight line until it hits a token. That token will determine the following state of the beam.
 */

package core;

import java.awt.Point;
import java.util.ArrayList;

public class LaserBeam {
    private Direction direction;
    private Point location;
    private ArrayList<PointWithDirection> pathHistory;


    public LaserBeam(Point location, Direction direction) {

        this.direction = direction;
        this.location = location;
        pathHistory = new ArrayList<PointWithDirection>();
        pathHistory.add(new PointWithDirection(location, direction));
    }


    //moves the LaserBeam by one cell in the current Direction
    public void move() {

        switch (direction) {
            case EAST:
                location = new Point(location.x + 1, location.y);
                break;
            case WEST:
                location = new Point(location.x - 1, location.y);
                break;
            case NORTH:
                location = new Point(location.x, location.y - 1);
                break;
            case SOUTH:
                location = new Point(location.x, location.y + 1);
                break;
            default:
                throw new IllegalArgumentException(); //if LaserBeam not movable, return false
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Direction: " + direction + " , Location: (" + location.getX() + "," + location.getY() + ")" + ", Path: " + pathHistory;
    }

    public ArrayList<PointWithDirection> getPathHistory() {
        return pathHistory;
    }

    public void setPathHistory(ArrayList<PointWithDirection> pathHistory) {
        this.pathHistory = pathHistory;
    }

    private boolean pathExistsInPathHistory(){
        for(PointWithDirection path: pathHistory){
            if(path.getDirection() == direction && path.getPoint().getY() == location.getY()
                    && path.getPoint().getX() == location.getX())
                return true;
        }
        return false;
    }

    public void updatePath() {
        //Existence of given path in path history is checked to prevent infinite loops caused by green mirrors
        if(pathExistsInPathHistory())
            direction = Direction.STUCK; //set beam direction to STUCK to prevent infinite loop
        pathHistory.add(new PointWithDirection(location, direction));
    }
}
