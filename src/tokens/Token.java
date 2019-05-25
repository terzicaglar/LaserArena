/**
 * Generic token class, where all other tokens are subclass of this class.
 */

package tokens;
import java.awt.Point;

import core.Direction;
import sides.Side;


public abstract class Token {
	/*	TODO Orientation (position) field can be added and MirrorDirection will be removed. Each token will have four orientations. For mirrors; orientation 1 and 3, i.e., SLASH, and
			2 and 4, i.e., BACK_SLASH, will be the same. For WhiteObstacle, all four orientations will be the same, for PurpleTarget each orientation will be different, etc.
	 */

	protected Point location;
	protected Side[] sides;

	public Token()
	{
		sides = new Side[4];
	}

	public Side[] getSides()
	{
		return sides;
	}

	public Side getSide(int index)
	{
		return sides[index];
	}
}
