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

    //protected Point location; //TODO: Currently, tokens do not have location field, in the future it can be added
    Map<Direction, Side> sides;

    public Orientation getOrientation() {
        return orientation;
    }

    Orientation orientation;

    String imageName;

    private boolean isPassed = false; //checks if a beam passes on this token

    boolean isOrientationFixed = false; //denotes if the orientation of the token can be changed by clicking

    boolean isLocationFixed = false; //denotes if the location of the token can be changed by clicking

    protected String getImageName() {
        return imageName;
    }

    Token() {
        isOrientationFixed = false;
        isLocationFixed = false;
        sides = new HashMap<>(4);
    }

    public Map getSides() {
        return sides;
    }

    public Side getSide(Direction direction) {
        return sides.get(direction);
    }

    public void nextOrientation() {
        this.orientation = orientation.nextOrientation();
        construct();
    }

    public boolean isTokenTypeSameWith(Token t)
    {
        return this.getClass().getName().equalsIgnoreCase(t.getClass().getName());

    }

    public String getTokenImageName() {
        if (isOrientationFixed)
            return imageName;
        else
            return imageName + "-Q";
    }

    public String getWaitingTokenImageName() {
        return this.getClass().getSimpleName() + "_Random";
    }

    public String getGrayedWaitingTokenImageName() {
        //TODO:For each token there may be one transparent image, to identify the waiting token when they are not active
        return getWaitingTokenImageName() + "_Transparent";
    }

    void createImageName() {
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
}
