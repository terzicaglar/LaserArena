/**
 * Generates a laser beam in the facing direction at the beginning. If an incoming laser hits it from any side,
 * it becomes stucked.
 */
package tokens;


import core.Direction;
import core.Orientation;
import sides.BackSlashReflectorSide;
import sides.SlashReflectorSide;
import sides.StuckableSide;
import sides.TargetableSide;

public class RedLaser extends Token {

    public RedLaser(Orientation orientation)
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
            //TODO: Implement this method
            //
        }
    }

}
