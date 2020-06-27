package core;

import java.awt.*;

class PointWithDirection {

    private Point point;
    private Direction direction;

    public PointWithDirection(Point point, Direction direction) {
        this.point = point;
        this.direction = direction;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "" + getPoint().getX() + " , " + getPoint().getY() + " - " + getDirection();
    }
}
