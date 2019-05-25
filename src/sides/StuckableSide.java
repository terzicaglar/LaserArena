package sides;

import core.Direction;
import interfaces.Stuckable;

public class StuckableSide extends Side implements Stuckable {
    @Override
    public Direction action(Direction direction) {
        return null;
    }
}
