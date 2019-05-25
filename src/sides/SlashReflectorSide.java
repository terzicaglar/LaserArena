package sides;

import core.Direction;
import core.MirrorDirection;
import interfaces.Reflector;

public class SlashReflectorSide extends Side implements Reflector {
    @Override
    public Direction action(Direction direction) {
        return reflect(direction, MirrorDirection.SLASH);
    }
}
