package sides;

import core.Direction;
import core.GameMap;
import core.LaserBeam;
import core.Orientation;
import interfaces.BeamCreator;
import interfaces.Reflector;
import interfaces.Transparent;

public class BackSlashReflectorTransparentSide extends Side implements Reflector, BeamCreator, Transparent {
    @Override
    public Direction action(LaserBeam laserBeam) {
        //New LaserBeam is created in the same location and direction
        GameMap.getInstance().addLaserBeam(new LaserBeam(laserBeam.getLocation(), pass(laserBeam.getDirection())));
        //Return the current laserBeam's new direction
        return reflect(laserBeam.getDirection(), Orientation.BACKSLASH_MIRROR);
    }
}
