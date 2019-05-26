/**
 * Standard mirror which reflects the incoming laser  by 90 degrees. Mirrors are placed in both directions of this token.
 */

package tokens;

import core.Direction;
import core.Orientation;
import sides.BackSlashReflectorSide;
import sides.SlashReflectorSide;

public class BlueMirror extends Token{
    public BlueMirror(Orientation orientation)
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
                //Slash Type Mirror "/"
                sides.put(Direction.NORTH, new SlashReflectorSide());
                sides.put(Direction.WEST, new SlashReflectorSide());
                sides.put(Direction.EAST, new SlashReflectorSide());
                sides.put(Direction.SOUTH, new SlashReflectorSide());
                break;
            case O1:
            case O3:
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
