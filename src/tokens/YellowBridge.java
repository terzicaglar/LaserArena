/**
 * Beam can pass in two opposing directions only (depends on the position of this token), otherwise beam is stucked.
 */
package tokens;

import interfaces.Stuckable;
import interfaces.Transparent;

public class YellowBridge extends Token implements Transparent, Stuckable {
}
