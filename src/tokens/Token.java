/**
 * Generic token class, where all other tokens are subclass of this class.
 */

package tokens;
import java.awt.Point;

import core.Direction;
import sides.Side;


public abstract class Token {
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
