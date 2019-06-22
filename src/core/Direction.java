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
	OUT_OF_BOUNDS; //used for Stucked Laser Beam

	private Direction opposite, backSlash, slash;
	private boolean moveable;

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

		NORTH.moveable = true;
		SOUTH.moveable = true;
		EAST.moveable = true;
		WEST.moveable = true;
		TARGET_HIT.moveable = false;
		STUCKED.moveable = false;
		OUT_OF_BOUNDS.moveable = false;
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
	public boolean moveable()
	{
		return moveable;
	}
}
