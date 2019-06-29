package core;

import tokens.PurpleTarget;
import tokens.RedLaser;
import tokens.Token;

import java.awt.*;
import java.util.ArrayList;

/**
 * GameMap of the game, where tokens are placed and laser(s) pass.
 */
public class GameMap {
	private static final int MAX_LOOP = 1000;
	private int width, height, noOfTargets;
	private static Token[][] tokens;
	private static ArrayList<LaserBeam> beams = new ArrayList<>(4);;
	public GameMap(int width, int height)
	{
		this.setWidth(width);
		this.setHeight(height);
		tokens = new Token[width][height];
	}

	public GameMap(int width, int height, int noOfTargets)
	{
		this.noOfTargets = noOfTargets;
		this.setWidth(width);
		this.setHeight(height);
		tokens = new Token[width][height];
	}
	
	public static Token getTokenLocatedInXY(int x, int y)
	{
		return tokens[x][y];
	}

	public static Token getTokenLocatedInPoint(Point p)
	{
		return tokens[(int)p.getX()][(int)p.getY()];
	}

	public boolean addToken(Token token, Point point)
	{
		if(point.getY() < height && point.getY() >= 0
			&& point.getX() < width && point.getX() >= 0)
		{
			tokens[(int) point.getX()][(int) point.getY()] = token;
			if(token instanceof RedLaser)
				addLaserBeam(new LaserBeam(point, ((RedLaser) token).getGeneratedLaserDirection()));
			return true;
		}
		else
			return false;
	}

	public void moveBeamsUntilNotMovable()
	{
		//TODO: not completed
		System.out.println("init beam:" + beams.get(0));
		LaserBeam beam;
		int i;
		for(int k = 0; k < beams.size(); k++)
		{
			beam = beams.get(k);
			i = 0;
			while(beam.getDirection().isMovable() && i < MAX_LOOP )
			{
				beam.move();
				if(isOutOfBounds(beam.getLocation()))
				{
					beam.setDirection(Direction.OUT_OF_BOUNDS);
				}
				else
				{
					Token t = getTokenLocatedInPoint(beam.getLocation());
					if(t != null)
					{
						beam.setDirection( t.getSide(beam.getDirection().getOppositeDirection()).action(beam));
					}

				}
				i++;

			}
			System.out.println( "final beam(s): " + beam);
		}

		/*while(direction.isMovable())
		{
			move();
		}*/
		//TODO: check if the LaserBeam loops indefinitely, not sure if it is possible???
	}

	private int getWantedMandatoryTargets()
	{
		int noOfWantedMandatoryTargets = 0;
		for(Token token_arr[]: tokens)
		{
			for(Token t: token_arr)
			{
				if(t instanceof PurpleTarget && ((PurpleTarget) t).isMandatoryTarget())
				{
					noOfWantedMandatoryTargets++;
				}
			}
		}
		return noOfWantedMandatoryTargets;
	}

	private boolean checkIfAllWantedTargetsHit()
	{
		//TODO: not tested
		int noOfMandatoryTargetsHit = 0;
		int noOfRandomTargetsHit = 0;
		for(LaserBeam beam: beams)
		{
			if(beam.getDirection() == Direction.TARGET_HIT)
				noOfRandomTargetsHit++;
			else if( beam.getDirection() == Direction.TARGET_HIT_MANDATORY)
				noOfMandatoryTargetsHit++;
		}

		return (noOfMandatoryTargetsHit == getWantedMandatoryTargets() &&
				(noOfMandatoryTargetsHit + noOfRandomTargetsHit) == noOfTargets);
	}

	private boolean isOutOfBounds(Point p)
	{
		return (p.getX() >= width || p.getY() >= height
					|| p.getX() < 0 || p.getY() < 0);
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public static ArrayList<LaserBeam> getBeams() {
		return beams;
	}

	public static void setBeams(ArrayList<LaserBeam> beams) {
		GameMap.beams = beams;
	}

	public static Token[][] getTokens() {
		return tokens;
	}

	public static void setTokens(Token[][] tokens) {
		GameMap.tokens = tokens;
	}

	public static boolean addLaserBeam(LaserBeam l) {
		return beams.add(l);
	}
}
