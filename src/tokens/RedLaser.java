/**
 * Generates a laser beam in the facing direction at the beginning. If an incoming laser hits it from any side,
 * it becomes stucked.
 */
package tokens;

import interfaces.BeamCreator;
import interfaces.Stuckable;

public class RedLaser extends Token implements Stuckable, BeamCreator {
}
