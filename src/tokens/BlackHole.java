

package tokens;

import core.Direction;
import core.Orientation;
import sides.StuckableSide;

import java.awt.*;

public class BlackHole extends Token {

    public BlackHole(boolean isLocationFixed)
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

    public BlackHole()
    {
        super();
        this.isLocationFixed = false;
        this.orientation = Orientation.NONE;
        this.isOrientationFixed = true;
        construct();
    }

    public String toIconString() {
        return "" + this.getClass().getSimpleName().charAt(0);
    }

    @Override
    protected void construct() {
        createImageName();
        sides.put(Direction.NORTH, new StuckableSide());
        sides.put(Direction.WEST, new StuckableSide());
        sides.put(Direction.EAST, new StuckableSide());
        sides.put(Direction.SOUTH, new StuckableSide());
    }

    @Override
    public void paintToken(Graphics g, int width, int height) {
        g.setColor(Color.DARK_GRAY);
        g.fillOval(0, 0, width, height);
    }
}
