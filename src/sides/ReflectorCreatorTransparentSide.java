package sides;

import core.Direction;
import interfaces.BeamCreator;
import interfaces.Reflector;
import interfaces.Transparent;

public class ReflectorCreatorTransparentSide extends Side implements Reflector, BeamCreator, Transparent {
    @Override
    public Direction action() {
        return null;
    }
}
