/**
 * An obstacle blocks the respective point on the GameMap, it does not affect the laser beam.
 */

package tokens;

import core.Direction;
import core.Orientation;
import sides.TransparentSide;

import java.awt.*;
import java.util.ArrayList;

public class WhiteObstacle extends Token {

    public WhiteObstacle(boolean isLocationFixed)
    {
        super();
        this.isLocationFixed = isLocationFixed;
        /*this.orientation = Orientation.NONE;
        possibleOrientations = new ArrayList<Orientation>();
        possibleOrientations.add(Orientation.NONE);*/
        construct();
    }

    public WhiteObstacle()
    {
        super();
        this.isLocationFixed = false;
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

    public void paintToken(Graphics g, int width, int height){
        g.setColor(Color.WHITE);
        g.fillOval(width/4, height/4, width/2, height/2);
    }
}
