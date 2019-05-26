/**
 * Beam can pass in two opposing directions only (depends on the position of this token), otherwise beam is stucked.
 */
package tokens;

import core.Direction;
import core.Orientation;
import interfaces.Stuckable;
import interfaces.Transparent;
import sides.StuckableSide;
import sides.TransparentSide;

public class YellowBridge extends Token implements Transparent, Stuckable {
    public YellowBridge(Orientation orientation)
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
            case O0:
            case O2:
                //Horizontal Bridge "--"
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new StuckableSide());
                sides.put(Direction.EAST, new TransparentSide());
                sides.put(Direction.WEST, new TransparentSide());
                break;
            case O1:
            case O3:
                //Vertical Bridge "|"
                sides.put(Direction.EAST, new StuckableSide());
                sides.put(Direction.WEST, new StuckableSide());
                sides.put(Direction.SOUTH, new TransparentSide());
                sides.put(Direction.NORTH, new TransparentSide());
                break;
            default:
                //TODO Type of exception may be a better than IllegalArgumentException
                throw new IllegalArgumentException();
        }
    }
}
