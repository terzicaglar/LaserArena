package core;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {
	
	static Map m;
	static int width = 5, height = 5;
	static ArenaPanel[][] panels;
	static LaserBeam lb;
	
	public static void main(String[] args) {
		//Boolean b1 = new Boolean();
		double d = 10;
		m = new Map(width, height);
		lb = new LaserBeam(m, new Point(2, 3), Direction.SOUTH);
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
		
		
		f.setLayout(new GridLayout(m.getWidth(),m.getHeight()));
	    f.setSize(500,500);  
	    f.setVisible(true);  
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
