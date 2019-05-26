/**
 * Direction enum which contains four main directions
 */
package core;

public enum Direction {
	NORTH,
	EAST, 
	SOUTH, 
	WEST,
	NONE; //used for Stucked Laser Beam

	private Direction opposite;

	static {
		NORTH.opposite = SOUTH;
		SOUTH.opposite = NORTH;
		EAST.opposite = WEST;
		WEST.opposite = EAST;
	}

	public Direction getOppositeDirection() {
		return opposite;
	}
}
