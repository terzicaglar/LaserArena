/**
 * Main class, which runs the game.
 */
package core;

import tokens.*;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

public class Main {
	
	static GameMap map;
	static int width = 5, height = 5;
	static ArenaPanel[][] panels;
	static LaserBeam lb;
	
	public static void main(String[] args) {
		//Boolean b1 = new Boolean();
		map = new GameMap(width, height);
		initMap();
		panels = new ArenaPanel[width][height];
		JFrame f = new JFrame("Laser Arena");
		
		for (int i = 0; i < panels.length; i++) {
			for (int j = 0; j < panels[i].length; j++) {
				panels[j][i] = new ArenaPanel(j, i);
				//panels[j][i].setToolTipText(j + "," + i);
				panels[j][i].setBorder(BorderFactory.createLineBorder(Color.black));
				f.getContentPane().add(panels[j][i]);
			}
		}
		
		
		f.setLayout(new GridLayout(map.getWidth(), map.getHeight()));
	    f.setSize(500,500);  
	    f.setVisible(true);  
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void initMap()
	{
		map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST), new Point(0,3));
		map.addToken(new PurpleTarget(Orientation.TARGET_ON_EAST), new Point(0,0));
		map.addToken(new PurpleTarget(Orientation.TARGET_ON_SOUTH), new Point(0,2));
		map.addToken(new PurpleTarget(Orientation.TARGET_ON_SOUTH), new Point(3,4));
		map.addToken(new PurpleTarget(Orientation.TARGET_ON_WEST), new Point(4,3));
		map.addToken(new BlueMirror(Orientation.SLASH_MIRROR), new Point(4,4));
		map.addToken(new BlueMirror(Orientation.BACKSLASH_MIRROR), new Point(2,2));
		map.addToken(new GreenMirror(Orientation.BACKSLASH_MIRROR), new Point(3,3));
		map.addToken(new GreenMirror(Orientation.SLASH_MIRROR), new Point(2,3));
		map.addToken(new YellowBridge(Orientation.HORIZONTAL_BRIDGE), new Point(0,1));
		map.addToken(new YellowBridge(Orientation.VERTICAL_BRIDGE), new Point(1,3));
		map.addToken(new WhiteObstacle(), new Point(1,2));
		map.addToken(new WhiteObstacle(), new Point(4,0));
	}
}
