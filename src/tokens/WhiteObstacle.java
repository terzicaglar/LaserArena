/**
 * An obstacle blocks the respective point on the GameMap, it does not affect the laser beam.
 */

package tokens;

import core.Direction;
import core.Orientation;
import sides.TransparentSide;

public class WhiteObstacle extends Token {

    public WhiteObstacle()
    {
        super();
        construct();
    }

    public String toIconString() {
        return "" + this.getClass().getSimpleName().charAt(0);

    }


    protected void construct()
    {
        sides.put(Direction.NORTH, new TransparentSide());
        sides.put(Direction.WEST, new TransparentSide());
        sides.put(Direction.EAST, new TransparentSide());
        sides.put(Direction.SOUTH, new TransparentSide());
    }
}
