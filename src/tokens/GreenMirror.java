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

import java.awt.*;
import java.util.ArrayList;

public class GreenMirror extends Token{
    public GreenMirror(Orientation orientation, boolean isFixed)
    {
        super();
        this.isFixed = isFixed;
        this.orientation = orientation;
        construct();
    }

    public GreenMirror()
    {
        super();
        possibleOrientations = new ArrayList<Orientation>();
        possibleOrientations.add(Orientation.SLASH_MIRROR);
        possibleOrientations.add(Orientation.BACKSLASH_MIRROR);
    }

    @Override
    public void paintToken(Graphics g, int width, int height) {
        g.setColor(Color.GREEN);
        if(orientation == Orientation.BACKSLASH_MIRROR)
            g.drawLine(0,0, width, height);
        else if(orientation == Orientation.SLASH_MIRROR)
            g.drawLine(0, height, width, 0);
    }

    public String toIconString() {
        switch(orientation) {
            case SLASH_MIRROR:
                return this.getClass().getSimpleName().charAt(0) + " /";
            case BACKSLASH_MIRROR:
                return this.getClass().getSimpleName().charAt(0) + " \\";
            default:
                return null;
        }

    }

    protected void construct()
    {
        switch(orientation)
        {
            case SLASH_MIRROR:
                //Slash Type Mirror "/"
                sides.put(Direction.NORTH, new SlashReflectorTransparentSide());
                sides.put(Direction.WEST, new SlashReflectorTransparentSide());
                sides.put(Direction.EAST, new SlashReflectorTransparentSide());
                sides.put(Direction.SOUTH, new SlashReflectorTransparentSide());
                break;
            case BACKSLASH_MIRROR:
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
