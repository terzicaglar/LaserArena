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

    public PurpleTarget(Orientation orientation, boolean isMandatoryTarget, boolean isOrientationFixed, boolean isLocationFixed)
    {
        super();
        this.isLocationFixed = isLocationFixed;
        this.isOrientationFixed = isOrientationFixed;
        this.orientation = orientation;
        this.isMandatoryTarget = isMandatoryTarget;
        construct();
    }

    public PurpleTarget(Orientation orientation) {
        super();
        this.orientation = orientation;
        this.isLocationFixed = false;
        this.isOrientationFixed = false;
        isMandatoryTarget = false;
        construct();
       /* possibleOrientations = new ArrayList<Orientation>();
        possibleOrientations.add(Orientation.TARGET_ON_SOUTH);
        possibleOrientations.add(Orientation.TARGET_ON_WEST);
        possibleOrientations.add(Orientation.TARGET_ON_EAST);
        possibleOrientations.add(Orientation.TARGET_ON_NORTH);*/
    }

    public String toIconString() {
        switch(orientation) {
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

    @Override
    public void paintToken(Graphics g, int width, int height) {
        g.setColor(Color.MAGENTA);
        int[] xPoints;
        int[] yPoints;
        xPoints = new int[3];
        yPoints = new int[3];
        if(orientation == Orientation.TARGET_ON_WEST)
        {
            g.drawLine(0, height, width, 0);
            xPoints[0] = 0;
            yPoints[0] = 0;
            xPoints[1] = width/2;
            yPoints[1] = height/2;
            xPoints[2] = 0;
            yPoints[2] = height;
        }
        else if(orientation == Orientation.TARGET_ON_EAST)
        {
            g.drawLine(0, height, width, 0);
            xPoints[0] = width;
            yPoints[0] = 0;
            xPoints[1] = width/2;
            yPoints[1] = height/2;
            xPoints[2] = width;
            yPoints[2] = height;
        }
        else if(orientation == Orientation.TARGET_ON_SOUTH)
        {
            g.drawLine(0,0, width, height);
            xPoints[0] = 0;
            yPoints[0] = height;
            xPoints[1] = width/2;
            yPoints[1] = height/2;
            xPoints[2] = width;
            yPoints[2] = height;
        }
        else if(orientation == Orientation.TARGET_ON_NORTH)
        {
            g.drawLine(0,0, width, height);
            xPoints[0] = 0;
            yPoints[0] = 0;
            xPoints[1] = width/2;
            yPoints[1] = height/2;
            xPoints[2] = width;
            yPoints[2] = 0;
        }

        g.fillPolygon(xPoints, yPoints, 3);
        if(isMandatoryTarget)
        {
            int[] triangleXPoints;
            int[] triangleYPoints;
            triangleXPoints = new int[3];
            triangleYPoints = new int[3];
            //draw a little red triangle if the target is mandatory target
            g.setColor(Color.RED);
            Point midPoint1 = findMidPoint(xPoints[0], yPoints[0], xPoints[2], yPoints[2]);
            Point midPoint2 = findMidPoint(midPoint1.x, midPoint1.y, xPoints[1], yPoints[1]);
            Point midPoint3 = findMidPoint(midPoint1.x, midPoint1.y, xPoints[0], yPoints[0]);
            Point midPoint4 = findMidPoint(midPoint1.x, midPoint1.y, xPoints[2], yPoints[2]);
            triangleXPoints[0] = midPoint3.x;
            triangleYPoints[0] = midPoint3.y;
            triangleXPoints[1] = midPoint4.x;
            triangleYPoints[1] = midPoint4.y;
            triangleXPoints[2] = midPoint2.x;
            triangleYPoints[2] = midPoint2.y;
            g.fillPolygon(triangleXPoints, triangleYPoints, 3);
        }
    }

    private Point findMidPoint(int x1, int y1, int x2, int y2)
    {
        return new Point((x1+x2)/2, (y1+y2)/2);
    }

    protected void construct()
    {
        createImageName();
        if(isMandatoryTarget)
            imageName += "-M";
        //TODO: Mandatory Target will be added
        switch(orientation)
        {
            case TARGET_ON_WEST: //Target on WEST, Stuckable on NORTH, Mirrors on EAST and SOUTH
                sides.put(Direction.SOUTH, new SlashReflectorSide());
                sides.put(Direction.NORTH, new StuckableSide());
                sides.put(Direction.EAST, new SlashReflectorSide());
                if(isMandatoryTarget)
                    sides.put(Direction.WEST, new MandatoryTargetableSide());
                else
                    sides.put(Direction.WEST, new TargetableSide());
                break;
            case TARGET_ON_NORTH: //Target on NORTH
                sides.put(Direction.SOUTH, new BackSlashReflectorSide());
                if(isMandatoryTarget)
                    sides.put(Direction.NORTH, new MandatoryTargetableSide());
                else
                    sides.put(Direction.NORTH, new TargetableSide());
                sides.put(Direction.EAST, new StuckableSide());
                sides.put(Direction.WEST, new BackSlashReflectorSide());
                break;
            case TARGET_ON_EAST: //Target on EAST
                sides.put(Direction.SOUTH, new StuckableSide());
                sides.put(Direction.NORTH, new SlashReflectorSide());
                if(isMandatoryTarget)
                    sides.put(Direction.EAST, new MandatoryTargetableSide());
                else
                    sides.put(Direction.EAST, new TargetableSide());
                sides.put(Direction.WEST, new SlashReflectorSide());
                break;
            case TARGET_ON_SOUTH: //Target on SOUTH
                if(isMandatoryTarget)
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
