package interfaces;
/**
 * Interface for Tokens that have reflecting capability.
 * Applicable Tokens: Blue Mirror, Green Mirror, and Purple Target
 */

import core.*;

public interface Reflector {

	//Reflected Direction is returned according to the coming directionOfBeam and the orientation
	default Direction reflect(Direction directionOfBeam, Orientation orientation)
	{
		switch (orientation)
		{
			case O0:
			case O2:
				switch(directionOfBeam) //for position of mirror like this: /
				{
					case NORTH: //beam is coming from top, i.e., NORTH
						return Direction.WEST; //beam goes to left, i.e., WEST
					case EAST: //EAST
						return Direction.SOUTH; //SOUTH
					case SOUTH:
						return Direction.EAST;
					case WEST:
						return Direction.NORTH;
				}
				break;
			case O1:
			case O3:
				switch(directionOfBeam) //for position of mirror like this: \
				{
					case NORTH: //beam is coming from top, i.e., NORTH
						return Direction.EAST; //beam goes to left, i.e., EAST
					case EAST: //EAST
						return Direction.NORTH; //NORTH
					case SOUTH:
						return Direction.WEST;
					case WEST:
						return Direction.SOUTH;

				}
				break;
			}
		throw new IllegalArgumentException();
	}
	
}
