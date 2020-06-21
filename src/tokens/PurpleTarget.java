/**
 * Multi-functional and the most crucial token in the game. It is a composition of mirror, stuckable, and target.
 * Mirror side reflects in the same way with BlueMirror. If Target is hit, beam is stuck ath there and lights the
 * Target. Aim of this game is hitting "labeled" Targets. If laser hits the empty edge, it becomes stuck, as well.
 */

package tokens;


import core.Direction;
import core.Orientation;
import sides.*;

import java.awt.*;

public class PurpleTarget extends Token {
    public boolean isMandatoryTarget() {
        return isMandatoryTarget;
    }

    private boolean isMandatoryTarget = false;

    public PurpleTarget(Orientation orientation, boolean isMandatoryTarget, boolean isOrientationFixed, boolean isLocationFixed) {
        super();
        this.isLocationFixed = isLocationFixed;
        this.isOrientationFixed = isOrientationFixed;
        this.orientation = orientation;
        this.isMandatoryTarget = isMandatoryTarget;
        construct();
    }

    public PurpleTarget(Orientation orientation, boolean isMandatoryTarget) {
        super();
        this.orientation = orientation;
        this.isLocationFixed = false;
        this.isOrientationFixed = true;
        this.isMandatoryTarget = isMandatoryTarget;
        construct();
    }

    public PurpleTarget(Orientation orientation) {
        super();
        this.orientation = orientation;
        this.isLocationFixed = false;
        this.isOrientationFixed = true;
        isMandatoryTarget = false;
        construct();
    }

    public PurpleTarget() {
        super();
        this.orientation = Orientation.TARGET_ON_SOUTH;
        this.isMandatoryTarget = false;
        construct();
    }

    public PurpleTarget(boolean isMandatoryTarget) {
        super();
        this.orientation = Orientation.TARGET_ON_SOUTH;
        this.isMandatoryTarget = isMandatoryTarget;
        construct();
    }

    public String toIconString() {
        switch (orientation) {
            case TARGET_ON_WEST:
                return this.getClass().getSimpleName().charAt(0) + " W/";
            case TARGET_ON_NORTH:
                return this.getClass().getSimpleName().charAt(0) + " N\\";
            case TARGET_ON_EAST:
                return this.getClass().getSimpleName().charAt(0) + " E/";
            case TARGET_ON_SOUTH:
                return this.getClass().getSimpleName().charAt(0) + " S\\";
            default:
                return null;
        }

    }

    private Point findMidPoint(int x1, int y1, int x2, int y2) {
        return new Point((x1 + x2) / 2, (y1 + y2) / 2);
    }

    @Override
    public String getWaitingTokenImageName() {
        if (isMandatoryTarget)
            return this.getClass().getSimpleName() + "Mandatory_Random";
        else
            return this.getClass().getSimpleName() + "_Random";
    }

//    @Override
//    public String getGrayedWaitingTokenImageName() {
//        if (isMandatoryTarget)
//            return "GrayMandatory_Random";
//        else
//            return "Gray_Random";
//    }


    protected void construct() {
        createImageName();
        if (isMandatoryTarget)
            imageName += "-M";
        //TODO: Mandatory Target will be added
        switch (orientation) {
            case TARGET_ON_WEST: //Target on WEST, Stuckable on NORTH, Mirrors on EAST and SOUTH
                sides.put(Direction.SOUTH, new SlashReflectorSide());
                sides.put(Direction.NORTH, new StuckableSide());
                sides.put(Direction.EAST, new SlashReflectorSide());
                if (isMandatoryTarget)
                    sides.put(Direction.WEST, new MandatoryTargetableSide());
                else
                    sides.put(Direction.WEST, new TargetableSide());
                break;
            case TARGET_ON_NORTH: //Target on NORTH
                sides.put(Direction.SOUTH, new BackSlashReflectorSide());
                if (isMandatoryTarget)
                    sides.put(Direction.NORTH, new MandatoryTargetableSide());
                else
                    sides.put(Direction.NORTH, new TargetableSide());
                sides.put(Direction.EAST, new StuckableSide());
                sides.put(Direction.WEST, new BackSlashReflectorSide());
                break;
            case TARGET_ON_EAST: //Target on EAST
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new SlashReflectorSide());
                if (isMandatoryTarget)
                    sides.put(Direction.EAST, new MandatoryTargetableSide());
                else
                    sides.put(Direction.EAST, new TargetableSide());
                sides.put(Direction.WEST, new SlashReflectorSide());
                break;
            case TARGET_ON_SOUTH: //Target on SOUTH
                if (isMandatoryTarget)
                    sides.put(Direction.SOUTH, new MandatoryTargetableSide());
                else
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
