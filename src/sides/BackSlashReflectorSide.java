package sides;

import core.Direction;
import core.Orientation;
import interfaces.Reflector;

public class BackSlashReflectorSide extends Side implements Reflector {
    @Override
    public Direction action(Direction direction) {
        return reflect(direction, Orientation.O1);
    }
}
