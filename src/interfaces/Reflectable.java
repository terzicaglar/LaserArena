package interfaces;
/**
 * Interface for Tokens that have reflecting capability, i.e., Blue Mirror, Green Mirror, and Purple Target
 */

import core.*;

public interface Reflectable {

	default Direction reflect(Direction directionOfBeam, int currentPosition)
	{
		System.out.println("interface reflect()");
		if(currentPosition == 0)
		{
			
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
		}
		else if(currentPosition == 1)
		{
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
		}
		return null;
	}
	
}
