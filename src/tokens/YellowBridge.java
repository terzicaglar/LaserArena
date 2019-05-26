/**
 * Beam can pass in two opposing directions only (depends on the position of this token), otherwise beam is stucked.
 */
package tokens;

import core.Orientation;
import interfaces.Stuckable;
import interfaces.Transparent;

public class YellowBridge extends Token implements Transparent, Stuckable {
    public YellowBridge(Orientation orientation)
    {
        super();
        this.orientation = orientation;
        construct();
    }

    //TODO This may be an abstract class in Token OR this may be done with setSides method, I have not decided on that
    private void construct()
    {


    }
}
