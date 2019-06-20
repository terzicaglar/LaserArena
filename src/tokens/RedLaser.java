/**
 * Generates a laser beam in the facing direction at the beginning. If an incoming laser hits it from any side,
 * it becomes stucked.
 */
package tokens;


import core.Direction;
import core.Orientation;
import sides.*;

public class RedLaser extends Token {

    public RedLaser(Orientation orientation)
    {
        super();
        this.orientation = orientation;
        construct();
    }

    protected void construct()
    {
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
