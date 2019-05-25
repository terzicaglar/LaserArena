package sides;

import core.Direction;
import core.MirrorDirection;
import interfaces.Reflector;

public class BackSlashReflectorSide extends Side implements Reflector {
    @Override
    public Direction action(Direction direction) {
        return reflect(direction, MirrorDirection.BACK_SLASH);
    }
}
