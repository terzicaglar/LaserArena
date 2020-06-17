package core;

/**
 * Orientation enum represents the position of the Token. Some tokens like WhiteObstacle may have only one Orientation. i.e., all four orientations have the same effect,
 *  while Mirrors have two Orientations (/ Type and \ Type), and PurpleTarget has four different orientations. Each Token will have four orientations, where in Mirrors O1 and O3
 *  and O0 and O2 have the same effect, where PurpleTarget's each Orientation has different effects (All orientations are rotations of the other ones).
 */

public enum Orientation {
    SLASH_MIRROR,
    BACKSLASH_MIRROR,
    HORIZONTAL_BRIDGE,
    VERTICAL_BRIDGE,
    GENERATOR_ON_WEST,
    GENERATOR_ON_EAST,
    GENERATOR_ON_NORTH,
    GENERATOR_ON_SOUTH,
    TARGET_ON_WEST,
    TARGET_ON_EAST,
    TARGET_ON_NORTH,
    TARGET_ON_SOUTH,
    NONE;

    private Orientation next;

    static{
        SLASH_MIRROR.next = BACKSLASH_MIRROR;
        BACKSLASH_MIRROR.next = SLASH_MIRROR;

        HORIZONTAL_BRIDGE.next = VERTICAL_BRIDGE;
        VERTICAL_BRIDGE.next = HORIZONTAL_BRIDGE;

        GENERATOR_ON_WEST.next = GENERATOR_ON_NORTH;
        GENERATOR_ON_NORTH.next = GENERATOR_ON_EAST;
        GENERATOR_ON_EAST.next = GENERATOR_ON_SOUTH;
        GENERATOR_ON_SOUTH.next = GENERATOR_ON_WEST;

        TARGET_ON_WEST.next = TARGET_ON_NORTH;
        TARGET_ON_NORTH.next = TARGET_ON_EAST;
        TARGET_ON_EAST.next = TARGET_ON_SOUTH;
        TARGET_ON_SOUTH.next = TARGET_ON_WEST;

        NONE.next = NONE;
    }

    public Orientation nextOrientation()
    {
        return next;
    }
}
