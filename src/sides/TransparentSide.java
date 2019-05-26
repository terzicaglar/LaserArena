package sides;

import core.Direction;
import interfaces.Transparent;

public class TransparentSide extends Side implements Transparent {
    @Override
    public Direction action(Direction direction) {
        return pass(direction);
    }
}
