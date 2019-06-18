package interfaces;
/**
 * Interface for Tokens that have reflecting capability.
 * Applicable Tokens: Blue Mirror, Green Mirror, and Purple Target
 */

import core.*;

public interface Reflector {

	//Reflected Direction is returned according to the coming directionOfBeam and the orientation of the Mirror
	default Direction reflect(Direction directionOfBeam, Orientation orientation)
	{
		switch (orientation)
		{
			case O0:
			case O2:
				//for position of slash mirror like this: /
				return directionOfBeam.getSlashDirection();
			case O1:
			case O3:
				//for position of back_slash mirror like this: \
				return directionOfBeam.getBackSlashDirection();
		}
		throw new IllegalArgumentException();
	}
	
}
