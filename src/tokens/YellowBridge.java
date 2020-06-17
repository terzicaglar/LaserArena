/**
 * Beam can pass in two opposing directions only (depends on the position of this token), otherwise beam is stuck.
 */
package tokens;

import core.Direction;
import core.Orientation;
import sides.StuckableSide;
import sides.TransparentSide;

import java.awt.*;

public class YellowBridge extends Token{
    public YellowBridge(Orientation orientation, boolean isOrientationFixed, boolean isLocationFixed)
    {
        super();
        this.isLocationFixed = isLocationFixed;
        this.isOrientationFixed = isOrientationFixed;
        this.orientation = orientation;
        construct();
    }

    public YellowBridge(Orientation orientation)
    {
        super();
        this.orientation = orientation;
        this.isLocationFixed = false;
        this.isOrientationFixed = false;
        construct();
        /*possibleOrientations = new ArrayList<Orientation>();
        possibleOrientations.add(Orientation.VERTICAL_BRIDGE);
        possibleOrientations.add(Orientation.HORIZONTAL_BRIDGE);*/
    }

    @Override
    public void paintToken(Graphics g, int width, int height) {
//        g.setColor(Color.YELLOW);
//        if(orientation == Orientation.VERTICAL_BRIDGE)
//            g.drawLine(width/2,0, width/2, height);
//        else if(orientation == Orientation.HORIZONTAL_BRIDGE)
//            g.drawLine(0, height/2, width, height/2);
    }

    public String toIconString() {
        switch(orientation) {
            case HORIZONTAL_BRIDGE:
                return this.getClass().getSimpleName().charAt(0) + " --";
            case VERTICAL_BRIDGE:
                return this.getClass().getSimpleName().charAt(0) + " |";
            default:
                return null;
        }

    }

    protected void construct()
    {
        createImageName();
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
