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

    //TODO This may be an abstract class in Token OR this may be done with setSides method, I have not decided on that
    private void construct()
    {
        switch(orientation)
        {
            case O0: //LaserGenerator on WEST
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new StuckableSide());
                sides.put(Direction.EAST, new StuckableSide());
                sides.put(Direction.WEST, new BeamCreatorStuckableSide());
                break;
            case O1: //LaserGenerator on NORTH
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new BeamCreatorStuckableSide());
                sides.put(Direction.EAST, new StuckableSide());
                sides.put(Direction.WEST, new StuckableSide());
                break;
            case O2: //LaserGenerator on EAST
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new StuckableSide());
                sides.put(Direction.EAST, new BeamCreatorStuckableSide());
                sides.put(Direction.WEST, new StuckableSide());
                break;
            case O3: //LaserGenerator on SOUTH
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

}
