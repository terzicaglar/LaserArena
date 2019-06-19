/**
 * Multi-functional and the most crucial token in the game. It is a composition of mirror, stuckable, and target.
 * Mirror side reflects in the same way with BlueMirror. If Target is hit, beam is stucked ath there and lights the
 * Target. Aim of this game is hitting "labeled" Targets. If laser hits the empty edge, it becomes stucked, as well.
 */

package tokens;


import core.Direction;
import core.Orientation;
import sides.BackSlashReflectorSide;
import sides.SlashReflectorSide;
import sides.StuckableSide;
import sides.TargetableSide;

public class PurpleTarget extends Token {
    public PurpleTarget(Orientation orientation)
    {
        super();
        this.orientation = orientation;
        construct();
    }

    protected void construct()
    {
        switch(orientation)
        {
            case O0: //Target on WEST, Stuckable on NORTH, Mirrors on EAST and SOUTH
                sides.put(Direction.SOUTH, new SlashReflectorSide());
                sides.put(Direction.NORTH, new StuckableSide());
                sides.put(Direction.EAST, new SlashReflectorSide());
                sides.put(Direction.WEST, new TargetableSide());
                break;
            case O1: //Target on NORTH
                sides.put(Direction.SOUTH, new BackSlashReflectorSide());
                sides.put(Direction.NORTH, new TargetableSide());
                sides.put(Direction.EAST, new StuckableSide());
                sides.put(Direction.WEST, new BackSlashReflectorSide());
                break;
            case O2: //Target on EAST
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new SlashReflectorSide());
                sides.put(Direction.EAST, new TargetableSide());
                sides.put(Direction.WEST, new SlashReflectorSide());
                break;
            case O3: //Target on SOUTH
                sides.put(Direction.SOUTH, new TargetableSide());
                sides.put(Direction.NORTH, new BackSlashReflectorSide());
                sides.put(Direction.EAST, new BackSlashReflectorSide());
                sides.put(Direction.WEST, new StuckableSide());
                break;
            default:
                //TODO Type of exception may be a better than IllegalArgumentException
                throw new IllegalArgumentException();
        }
    }
	
}
