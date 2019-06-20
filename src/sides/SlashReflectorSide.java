package sides;

import core.Direction;
import core.LaserBeam;
import core.Orientation;
import interfaces.Reflector;

public class SlashReflectorSide extends Side implements Reflector {
    @Override
    public Direction action(LaserBeam laserBeam) {
        return reflect(laserBeam.getDirection(), Orientation.SLASH_MIRROR);
    }
}
