/**
 * Interface for Tokens that may cause laser beam to be stucked.
 * Applicable Tokens: Red, Purple, Yellow
 */

package interfaces;

import core.Direction;

public interface Stuckable {
    default Direction stuck()
    {
        return Direction.STUCKED;
    }
}
