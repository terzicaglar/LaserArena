/**
 * Standard mirror which reflects the incoming laser  by 90 degrees. Mirrors are placed in both directions of this token.
 */

package tokens;

import core.Orientation;
import sides.BackSlashReflectorSide;
import sides.SlashReflectorSide;

public class BlueMirror extends Token{
    private Orientation orientation;
    public BlueMirror(Orientation orientation)
    {
        super();
        this.orientation = orientation;
        construct();
    }

    //TODO This may be an abstract class in Token OR this may be done with setSides method, I have not decided on that
    private void construct()
    {
        for (int i = 0; i < sides.length; i++) {
            switch(orientation)
            {
                case O0:
                case O2:
                    sides[i] = new SlashReflectorSide();
                    break;
                case O1:
                case O3:
                    sides[i] = new BackSlashReflectorSide();
                    break;
                default:
                    //TODO Type of exception may be a better than IllegalArgumentException
                    throw new IllegalArgumentException();
            }
        }

    }



}
