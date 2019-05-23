package core;

import tokens.Token;

public class Map{
	private int width, height;
	private static Token[][] tokens;
	
	public Map(int width, int height)
	{
		this.setWidth(width);
		this.setWidth(height);
		tokens = new Token[width][height];
	}
	
	public static Token getShapeLocatedInXY(int x, int y)
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
