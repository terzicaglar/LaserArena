package sides;

import core.Direction;
import core.LaserBeam;
import interfaces.Targetable;

public class TargetableSide extends Side implements Targetable {
    @Override
    public Direction action(LaserBeam laserBeam) {
        return target();

    }
}
