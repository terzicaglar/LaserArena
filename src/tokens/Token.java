/**
 * Generic token class, where all other tokens are subclass of this class.
 */

package tokens;
import java.awt.Point;

import core.Direction;


public class Token {

	Direction direction;
	int noOfDirections;
	Point location;
	

	public Direction action()
	{
		return null;
	}
	
}
