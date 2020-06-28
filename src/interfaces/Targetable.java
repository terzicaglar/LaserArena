/**
 * Interface for Tokens that can be a Target.
 * Applicable Tokens: Purple
 */

package interfaces;

import core.Direction;

public interface Targetable {
    default Direction target(boolean isMandatoryTarget) {
        //TODO: return type of these kinds of methods may be Tuple <Direction,Status>
        if (isMandatoryTarget)
            return Direction.MANDATORY_TARGET_HIT;
        else
            return Direction.TARGET_HIT;
    }
}
