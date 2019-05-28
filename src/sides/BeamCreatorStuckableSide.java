package sides;

import core.Direction;
import core.LaserBeam;
import interfaces.BeamCreator;
import interfaces.Stuckable;

//TODO This Class may be deleted in the future, since it works like Stuckable
public class BeamCreatorStuckableSide extends Side implements BeamCreator, Stuckable {

    @Override
    public Direction action(LaserBeam laserBeam) {
        return stuck();
    }
}
