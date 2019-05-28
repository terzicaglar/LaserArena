package sides;

import core.Direction;
import core.LaserBeam;
import interfaces.Transparent;

public class TransparentSide extends Side implements Transparent {
    @Override
    public Direction action(LaserBeam laserBeam) {
        return pass(laserBeam.getDirection());
    }
}
