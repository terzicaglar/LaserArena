package sides;

import core.Direction;
import core.LaserBeam;
import interfaces.BeamCreator;
import interfaces.Reflector;
import interfaces.Transparent;

public class SlashReflectorTransparentSide extends Side implements Reflector, BeamCreator, Transparent {
    @Override
    public Direction action(LaserBeam laserBeam) {
        return null;
    }
}
