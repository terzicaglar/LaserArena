/**
 * Direction enum which contains four main directions
 */
package core;

public enum Direction {
	NORTH,
	EAST, 
	SOUTH, 
	WEST,
	TARGET_HIT,// TODO: not sure of this
	NONE; //used for Stucked Laser Beam

	private Direction opposite, backSlash, slash;

	static {
		NORTH.opposite = SOUTH;
		SOUTH.opposite = NORTH;
		EAST.opposite = WEST;
		WEST.opposite = EAST;

		NORTH.backSlash = EAST;
		SOUTH.backSlash = WEST;
		EAST.backSlash = NORTH;
		WEST.backSlash = SOUTH;

		NORTH.slash = WEST;
		SOUTH.slash = EAST;
		EAST.slash = SOUTH;
		WEST.slash = NORTH;
	}

	public Direction getOppositeDirection() {
		return opposite;
	}
	public Direction getSlashDirection() {
		return slash;
	}
	public Direction getBackSlashDirection() {
		return backSlash;
	}
}
