/**
 * Generic token class, where all other tokens are subclass of this class.
 */

package tokens;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import core.Direction;
import core.Orientation;
import sides.Side;


public abstract class Token {
	/*	TODO Orientation (position) field can be added and Orientation will be removed. Each token will have four orientations. For mirrors; orientation 1 and 3, i.e., SLASH, and
			2 and 4, i.e., BACK_SLASH, will be the same. For WhiteObstacle, all four orientations will be the same, for PurpleTarget each orientation will be different, etc.
	 */
	protected static int UNIQUE_ID = 0;
	protected int id;
	//protected Point location;
	protected Map<Direction, Side> sides;
	protected Orientation orientation;

	public Token()
	{
		sides = new HashMap<>(4);
	}

	public Map getSides()
	{
		return sides;
	}

	public Side getSide(Direction direction)
	{
		return sides.get(direction);
	}
}
