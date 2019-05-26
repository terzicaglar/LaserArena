/**
 * GameMap of the game, where tokens are placed and laser(s) pass.
 */
package core;

import tokens.Token;

import java.awt.*;

public class GameMap {
	private int width, height;
	private static Token[][] tokens;
	
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
}
