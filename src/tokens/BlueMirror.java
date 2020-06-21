/**
 * Standard mirror which reflects the incoming laser  by 90 degrees. Mirrors are placed in both directions of this token.
 */

package tokens;

import core.Direction;
import core.Orientation;
import sides.BackSlashReflectorSide;
import sides.SlashReflectorSide;

public class BlueMirror extends Token {


    public BlueMirror(Orientation orientation, boolean isOrientationFixed, boolean isLocationFixed) {
        super();
        this.isLocationFixed = isLocationFixed;
        this.isOrientationFixed = isOrientationFixed;
        this.orientation = orientation;
        construct();
    }

    public BlueMirror(Orientation orientation) {
        super();
        this.orientation = orientation;
        this.isLocationFixed = false;
        this.isOrientationFixed = true;
        construct();
        /*possibleOrientations = new ArrayList<Orientation>();
        possibleOrientations.add(Orientation.SLASH_MIRROR);
        possibleOrientations.add(Orientation.BACKSLASH_MIRROR);*/
    }

    public BlueMirror() {
        super();
        this.orientation = Orientation.SLASH_MIRROR;
        construct();
    }

    @Override
    public String toIconString() {
        switch (orientation) {
            case SLASH_MIRROR:
                return this.getClass().getSimpleName().charAt(0) + " /";
            case BACKSLASH_MIRROR:
                return this.getClass().getSimpleName().charAt(0) + " \\";
            default:
                return null;
        }

    }

    protected void construct() {
        createImageName();
        switch (orientation) {
            case SLASH_MIRROR:
                //Slash Type Mirror "/"
                sides.put(Direction.NORTH, new SlashReflectorSide());
                sides.put(Direction.WEST, new SlashReflectorSide());
                sides.put(Direction.EAST, new SlashReflectorSide());
                sides.put(Direction.SOUTH, new SlashReflectorSide());
                break;
            case BACKSLASH_MIRROR:
                //BackSlash Type Mirror "\"
                sides.put(Direction.NORTH, new BackSlashReflectorSide());
                sides.put(Direction.WEST, new BackSlashReflectorSide());
                sides.put(Direction.EAST, new BackSlashReflectorSide());
                sides.put(Direction.SOUTH, new BackSlashReflectorSide());
                break;
            default:
                //TODO Type of exception may be a better than IllegalArgumentException
                throw new IllegalArgumentException();
        }
    }


}
