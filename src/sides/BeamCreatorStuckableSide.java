package sides;

import core.Direction;
import interfaces.BeamCreator;
import interfaces.Stuckable;

public class BeamCreatorStuckableSide extends Side implements BeamCreator, Stuckable {

    @Override
    public Direction action(Direction direction) {
        return null;
    }
}
