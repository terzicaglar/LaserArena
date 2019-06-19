/**
 * Just like BlueMirror, reflects incoming laser by 90 degrees and mirrors are placed in both directions of this token. In addition,
 * it creates a new laser beam that transparently passes through the mirror.
 */

package tokens;

import core.Direction;
import core.Orientation;
import sides.BackSlashReflectorSide;
import sides.BackSlashReflectorTransparentSide;
import sides.SlashReflectorSide;
import sides.SlashReflectorTransparentSide;

public class GreenMirror extends Token{
    public GreenMirror(Orientation orientation)
    {
        super();
        this.orientation = orientation;
        construct();
    }

    protected void construct()
    {
        switch(orientation)
        {
            case O0:
            case O2:
                //Slash Type Mirror "/"
                sides.put(Direction.NORTH, new SlashReflectorTransparentSide());
                sides.put(Direction.WEST, new SlashReflectorTransparentSide());
                sides.put(Direction.EAST, new SlashReflectorTransparentSide());
                sides.put(Direction.SOUTH, new SlashReflectorTransparentSide());
                break;
            case O1:
            case O3:
                //BackSlash Type Mirror "\"
                sides.put(Direction.NORTH, new BackSlashReflectorTransparentSide());
                sides.put(Direction.WEST, new BackSlashReflectorTransparentSide());
                sides.put(Direction.EAST, new BackSlashReflectorTransparentSide());
                sides.put(Direction.SOUTH, new BackSlashReflectorTransparentSide());
                break;
            default:
                //TODO Type of exception may be a better than IllegalArgumentException
                throw new IllegalArgumentException();
        }
    }
}
