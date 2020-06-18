/**
 * An obstacle blocks the respective point on the GameMap, it does not affect the laser beam.
 */

package tokens;

import core.Direction;
import core.Orientation;
import sides.TransparentSide;

public class WhiteObstacle extends Token {

    public WhiteObstacle(boolean isLocationFixed)
    {
        super();
        this.isLocationFixed = isLocationFixed;
        this.orientation = Orientation.NONE;
        this.isOrientationFixed = true;
        /*this.orientation = Orientation.NONE;
        possibleOrientations = new ArrayList<Orientation>();
        possibleOrientations.add(Orientation.NONE);*/
        construct();
    }

    public WhiteObstacle()
    {
        super();
        this.isLocationFixed = false;
        this.orientation = Orientation.NONE;
        this.isOrientationFixed = true; //Since there is only one orientation, i.e., Orientation.NONE
        construct();
    }

    public String toIconString() {
        return "" + this.getClass().getSimpleName().charAt(0);

    }


    protected void construct()
    {
        createImageName();
        sides.put(Direction.NORTH, new TransparentSide());
        sides.put(Direction.WEST, new TransparentSide());
        sides.put(Direction.EAST, new TransparentSide());
        sides.put(Direction.SOUTH, new TransparentSide());
    }

}
