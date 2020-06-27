/**
 * Interface for Tokens that can create new Laser Beams.
 * Applicable Tokens: Green, Red
 */
package interfaces;

import core.Direction;
import core.LaserBeam;

import java.awt.*;

public interface BeamCreator {

    //TODO check if point is a valid point in the GameMap
    default LaserBeam createBeam(Point point, Direction direction) {
        return new LaserBeam(point, direction);
    }
}
