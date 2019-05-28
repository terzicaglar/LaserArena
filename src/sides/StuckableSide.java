package sides;

import core.Direction;
import core.LaserBeam;
import interfaces.Stuckable;

public class StuckableSide extends Side implements Stuckable {
    @Override
    public Direction action(LaserBeam laserBeam) {
        return stuck();
    }
}
