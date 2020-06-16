/**
 * Generic token class, where all other tokens are subclass of this class.
 */

package tokens;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import core.Direction;
import core.Orientation;
import sides.Side;

import javax.imageio.ImageIO;


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

	protected String imageName;

	private boolean isPassed = false; //checks if a beam passes on this token

	protected boolean isOrientationFixed = false; //denotes if the orientation of the token can be changed by clicking

	protected boolean isLocationFixed = false; //denotes if the location of the token can be changed by clicking

	protected String getImageName() {return imageName;}

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

	public void paintIfLocationFixed(Graphics g, int width, int height)
	{
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		if(!isOrientationFixed)
			g.drawString("?", width/4 , height/4);
		g.setColor(c);
	}

	//TODO: for empty cells, we can use blank cell images and delete drawn lines
	public void drawTokenImage(Graphics g, int width, int height)
	{
		BufferedImage img = null;
		try {
			System.out.println(imageName);
			img = ImageIO.read(new File("img/" + imageName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.drawImage(img, 0, 0, width, height, null);
	}

	protected void createImageName()
	{
		imageName = this.getClass().getSimpleName() + "-" + orientation;
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

	//TODO: should be deleted, since we are using graphics now
	abstract public String toIconString();

	abstract protected void construct();

	//TODO: paintToken() will be deleted, we now use drawTokenImage()
	abstract public void paintToken(Graphics g, int width, int height);
}
