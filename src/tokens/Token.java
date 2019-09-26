/**
 * Generic token class, where all other tokens are subclass of this class.
 */

package tokens;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.Direction;
import core.Orientation;
import sides.Side;


public abstract class Token {
	//TODO: New Tokens (BlackHole and Portal) will be added
	/*	TODO Orientation (position) field can be added and Orientation will be removed. Each token will have four orientations. For mirrors; orientation 1 and 3, i.e., SLASH, and
			2 and 4, i.e., BACK_SLASH, will be the same. For WhiteObstacle, all four orientations will be the same, for PurpleTarget each orientation will be different, etc.
	 */
	protected static int UNIQUE_ID = 0;
	protected int id;
	//protected Point location; //TODO: Currently, tokens do not have location field, in the future it can be added
	protected Map<Direction, Side> sides;
	protected Orientation orientation;

	private boolean isPassed = false; //checks if a beam passes on this token

	protected boolean isOrientationFixed = false; //denotes if the orientation of the token can be changed by clicking

	protected boolean isLocationFixed = false; //denotes if the location of the token can be changed by clicking

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

	public void nextOrientation(){
		this.orientation = orientation.nextOrientation();
		construct();
	}

	public boolean isLocationFixed() {
		return isLocationFixed;
	}

	public void setLocationFixed(boolean locationFixed) {
		isLocationFixed = locationFixed;
	}

	public boolean isOrientationFixed() {
		return isOrientationFixed;
	}

	public void setOrientationFixed(boolean orientationFixed) {
		isOrientationFixed = orientationFixed;
	}

	public void setPassed(boolean passed) {
		isPassed = passed;
	}

	public boolean isPassed() {
		return isPassed;
	}

	public ArrayList<Orientation> possibleOrientations;

	abstract public String toIconString();

	abstract protected void construct();

	abstract public void paintToken(Graphics g, int width, int height);
}
