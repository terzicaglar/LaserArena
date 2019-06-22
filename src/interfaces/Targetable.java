/**
 * Interface for Tokens that can be a Target.
 * Applicable Tokens: Purple
 */

package interfaces;

import core.Direction;

public interface Targetable {
    default Direction target()
    {
        //TODO: return type of these kinds of methods should be Tuple <Direction,Status>
        return Direction.TARGET_HIT;
    }
}
