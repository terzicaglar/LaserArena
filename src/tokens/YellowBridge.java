/**
 * Beam can pass in two opposing directions only (depends on the position of this token), otherwise beam is stucked.
 */
package tokens;

import core.Direction;
import core.Orientation;
import sides.StuckableSide;
import sides.TransparentSide;

public class YellowBridge extends Token{
    public YellowBridge(Orientation orientation)
    {
        super();
        this.orientation = orientation;
        construct();
    }

    protected void construct()
    {

        switch(orientation)
        {
            case VERTICAL_BRIDGE:
                //Vertical Bridge "|"
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new StuckableSide());
                sides.put(Direction.EAST, new TransparentSide());
                sides.put(Direction.WEST, new TransparentSide());
                break;
            case HORIZONTAL_BRIDGE:
                //Horizontal Bridge "--"
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
