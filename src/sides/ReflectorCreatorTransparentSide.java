package sides;

import core.Direction;
import interfaces.BeamCreator;
import interfaces.Reflector;
import interfaces.Transparent;

//TODO Create two classes for ReflectorCreatorTransparentSide, which are SlashReflectorCreatorTransparentSide and BackSlashReflectorCreatorTransparentSide
public class ReflectorCreatorTransparentSide extends Side implements Reflector, BeamCreator, Transparent {
    @Override
    public Direction action(Direction direction) {
        return null;
    }
}
