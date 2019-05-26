/**
 * Interface for Tokens that can be a Target.
 * Applicable Tokens: Purple
 */

package interfaces;

import core.Direction;

public interface Targetable {
    default Direction target()
    {
        return Direction.TARGET_HIT;
    }
}
