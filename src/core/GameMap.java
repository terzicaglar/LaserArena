package core;

import tokens.RedLaser;
import tokens.Token;

import java.awt.*;
import java.util.ArrayList;

/**
 * GameMap of the game, where tokens are placed and laser(s) pass.
 */
public class GameMap {
	private int width, height;
	private static Token[][] tokens;
	private static ArrayList<LaserBeam> lasers = new ArrayList<>(4);;
	public GameMap(int width, int height)
	{
		this.setWidth(width);
		this.setHeight(height);
		tokens = new Token[width][height];
	}
	
	public static Token getTokenLocatedInXY(int x, int y)
	{
		return tokens[x][y];
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

	public static ArrayList<LaserBeam> getLasers() {
		return lasers;
	}

	public static void setLasers(ArrayList<LaserBeam> lasers) {
		GameMap.lasers = lasers;
	}

	public static Token[][] getTokens() {
		return tokens;
	}

	public static void setTokens(Token[][] tokens) {
		GameMap.tokens = tokens;
	}

	public static boolean addLaserBeam(LaserBeam l) {
		return lasers.add(l);
	}
}
