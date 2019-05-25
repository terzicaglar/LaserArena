/**
 * Standard mirror which reflects the incoming laser  by 90 degrees. Mirrors are placed in both directions of this token.
 */

package tokens;

import core.MirrorDirection;
import sides.BackSlashReflectorSide;
import sides.Side;
import sides.SlashReflectorSide;

public class BlueMirror extends Token{
    private MirrorDirection mirrorDirection;
    public BlueMirror(MirrorDirection mirrorDirection)
    {
        super();
        this.mirrorDirection = mirrorDirection;
        construct();
    }

    //TODO This may be an abstract class in Token OR this may be done with setSides method, I have not decided on that
    private void construct()
    {
        for (int i = 0; i < sides.length; i++) {
            switch(mirrorDirection)
            {
                case SLASH:
                    sides[i] = new SlashReflectorSide();
                    break;
                case BACK_SLASH:
                    sides[i] = new BackSlashReflectorSide();
                    break;
                default:
                    //TODO Type of exception may be a better than IllegalArgumentException
                    throw new IllegalArgumentException();
            }
        }

    }



}
