/**
 * Each Token has 4 sides and every side has different property according to their type, i.e., implemented interface
 */

package sides;

import core.Direction;

public abstract class Side {


    //action method called when a laser beam hits the Side object
    public abstract Direction action();

}
