package sides;

import core.Direction;
import core.LaserBeam;
import interfaces.Targetable;


public class MandatoryTargetableSide extends Side implements Targetable {
    @Override
    public Direction action(LaserBeam laserBeam) {
        return target(true);

    }
}
