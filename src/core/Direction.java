/**
 * Direction enum which contains four main directions
 */
package core;

//TODO: Direction may be divided into two enums. Other enum may include status info like TARGET_HIT, STUCKED, OUT+OF_BOUNDS
public enum Direction {
	NORTH,
	EAST, 
	SOUTH, 
	WEST,
	TARGET_HIT,
	STUCKED,
	OUT_OF_BOUNDS; //used for Laser Beam out of Map

	private Direction opposite, backSlash, slash;
	private boolean movable;

	static {
		NORTH.opposite = SOUTH;
		SOUTH.opposite = NORTH;
		EAST.opposite = WEST;
		WEST.opposite = EAST;

		NORTH.slash = EAST;
		SOUTH.slash = WEST;
		EAST.slash = NORTH;
		WEST.slash = SOUTH;

		NORTH.backSlash = WEST;
		SOUTH.backSlash = EAST;
		EAST.backSlash = SOUTH;
		WEST.backSlash = NORTH;

		NORTH.movable = true;
		SOUTH.movable = true;
		EAST.movable = true;
		WEST.movable = true;
		TARGET_HIT.movable = false;
		STUCKED.movable = false;
		OUT_OF_BOUNDS.movable = false;
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
	public boolean isMovable()
	{
		return movable;
	}
}
