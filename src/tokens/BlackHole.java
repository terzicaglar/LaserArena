package tokens;

import core.Direction;
import core.Orientation;
import sides.StuckableSide;

//TODO: This class is not used, when decided to be added, should be put in necessary places, like ArenaFrame.getTokenFromShortName()
public class BlackHole extends Token {

    public BlackHole(boolean isLocationFixed) {
        super();
        this.isLocationFixed = isLocationFixed;
        this.orientation = Orientation.NONE;
        this.isOrientationFixed = true;
        /*this.orientation = Orientation.NONE;
        possibleOrientations = new ArrayList<Orientation>();
        possibleOrientations.add(Orientation.NONE);*/
        construct();
    }

    public BlackHole() {
        super();
        this.isLocationFixed = false;
        this.orientation = Orientation.NONE;
        this.isOrientationFixed = true;
        construct();
    }

    @Override
    public String toIconString() {
        return "BH";
    }

    @Override
    protected void construct() {
        createImageName();
        sides.put(Direction.NORTH, new StuckableSide());
        sides.put(Direction.WEST, new StuckableSide());
        sides.put(Direction.EAST, new StuckableSide());
        sides.put(Direction.SOUTH, new StuckableSide());
    }

}
