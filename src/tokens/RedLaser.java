/**
 * Generates a laser beam in the facing direction at the beginning. If an incoming laser hits it from any side,
 * it becomes stuck.
 */
package tokens;


import core.Direction;
import core.Orientation;
import sides.*;

import java.awt.*;

public class RedLaser extends Token {

    public RedLaser(Orientation orientation, boolean isOrientationFixed, boolean isLocationFixed)
    {
        super();
        this.isLocationFixed = isLocationFixed;
        this.isOrientationFixed = isOrientationFixed;
        this.orientation = orientation;
        construct();
    }

    public RedLaser(Orientation orientation)
    {
        super();
        this.orientation = orientation;
        this.isLocationFixed = false;
        this.isOrientationFixed = false;
        construct();
        /*possibleOrientations = new ArrayList<Orientation>();
        possibleOrientations.add(Orientation.GENERATOR_ON_WEST);
        possibleOrientations.add(Orientation.GENERATOR_ON_EAST);
        possibleOrientations.add(Orientation.GENERATOR_ON_NORTH);
        possibleOrientations.add(Orientation.GENERATOR_ON_SOUTH);*/
    }

    @Override
    public void paintToken(Graphics g, int width, int height) {
        g.setColor(Color.RED);
        int[] xPoints;
        int[] yPoints;
        xPoints = new int[3];
        yPoints = new int[3];
        if(orientation == Orientation.GENERATOR_ON_EAST){
            xPoints[0] = 0;
            yPoints[0] = 0;
            xPoints[1] = width/2;
            yPoints[1] = height/2;
            xPoints[2] = 0;
            yPoints[2] = height;
        }
        else if(orientation == Orientation.GENERATOR_ON_WEST)
        {
            xPoints[0] = width;
            yPoints[0] = 0;
            xPoints[1] = width/2;
            yPoints[1] = height/2;
            xPoints[2] = width;
            yPoints[2] = height;
        }
        else if(orientation == Orientation.GENERATOR_ON_NORTH)
        {
            xPoints[0] = 0;
            yPoints[0] = height;
            xPoints[1] = width/2;
            yPoints[1] = height/2;
            xPoints[2] = width;
            yPoints[2] = height;
        }
        else if(orientation == Orientation.GENERATOR_ON_SOUTH)
        {
            xPoints[0] = 0;
            yPoints[0] = 0;
            xPoints[1] = width/2;
            yPoints[1] = height/2;
            xPoints[2] = width;
            yPoints[2] = 0;
        }
        g.fillPolygon(xPoints, yPoints, 3);
    }

    public String toIconString() {
        switch(orientation) {
            case GENERATOR_ON_WEST:
                return this.getClass().getSimpleName().charAt(0) + " W";
            case GENERATOR_ON_NORTH:
                return this.getClass().getSimpleName().charAt(0) + " N";
            case GENERATOR_ON_EAST:
                return this.getClass().getSimpleName().charAt(0) + " E";
            case GENERATOR_ON_SOUTH:
                return this.getClass().getSimpleName().charAt(0) + " S";
            default:
                return null;
        }

    }

    protected void construct()
    {
        createImageName();
        switch(orientation)
        {
            case GENERATOR_ON_WEST: //LaserGenerator on WEST
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new StuckableSide());
                sides.put(Direction.EAST, new StuckableSide());
                sides.put(Direction.WEST, new BeamCreatorStuckableSide());
                break;
            case GENERATOR_ON_NORTH: //LaserGenerator on NORTH
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new BeamCreatorStuckableSide());
                sides.put(Direction.EAST, new StuckableSide());
                sides.put(Direction.WEST, new StuckableSide());
                break;
            case GENERATOR_ON_EAST: //LaserGenerator on EAST
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new StuckableSide());
                sides.put(Direction.EAST, new BeamCreatorStuckableSide());
                sides.put(Direction.WEST, new StuckableSide());
                break;
            case GENERATOR_ON_SOUTH: //LaserGenerator on SOUTH
                sides.put(Direction.SOUTH, new BeamCreatorStuckableSide());
                sides.put(Direction.NORTH, new StuckableSide());
                sides.put(Direction.EAST, new StuckableSide());
                sides.put(Direction.WEST, new StuckableSide());
                break;
            default:
                //TODO Type of exception may be a better than IllegalArgumentException
                throw new IllegalArgumentException();
        }
    }

    public Direction getGeneratedLaserDirection()
    {
        switch(orientation)
        {
            case GENERATOR_ON_WEST: //LaserGenerator on WEST
                return Direction.WEST;
            case GENERATOR_ON_NORTH: //LaserGenerator on NORTH
                return Direction.NORTH;
            case GENERATOR_ON_EAST: //LaserGenerator on EAST
                return Direction.EAST;
            case GENERATOR_ON_SOUTH: //LaserGenerator on SOUTH
                return Direction.SOUTH;
            default:
                //TODO Type of exception may be a better than IllegalArgumentException
                throw new IllegalArgumentException();
        }
    }

}
