/**
 * GameMap of the game, where tokens are placed and laser(s) pass.
 */
package core;

import tokens.Token;

public class GameMap {
	private int width, height;
	private static Token[][] tokens;
	
	public GameMap(int width, int height)
	{
		this.setWidth(width);
		this.setWidth(height);
		tokens = new Token[width][height];
	}
	
	public static Token getTokenLocatedInXY(int x, int y)
	{
		return tokens[x][y];
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
