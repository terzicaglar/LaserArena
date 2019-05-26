package core;

/**
 * Orientation enum represents the position of the Token. Some tokens like WhiteObstacle may have only one Orientation. i.e., all four orientations have the same effect,
 *  while Mirrors have two Orientations (/ Type and \ Type), and PurpleTarget has four different orientations. Each Token will have four orientations, where in Mirrors O1 and O3
 *  and O0 and O2 have the same effect, where PurpleTarget's each Orientation has different effects (All orientations are rotations of the other ones).
 */

public enum Orientation {
    O0,
    O1,
    O2,
    O3;
}
