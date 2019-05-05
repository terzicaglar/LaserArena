package core;

import shapes.Shape;

public class Map{
	private int width, height;
	private Shape[][] shapes;
	
	public Map(int width, int height)
	{
		this.setWidth(width);
		this.setWidth(height);
		shapes = new Shape[width][height];
	}
	
	public Shape getShapeLocatedInXY(int x, int y)
	{
		return shapes[x][y];
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
