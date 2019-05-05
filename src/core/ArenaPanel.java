package core;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class ArenaPanel extends JPanel {
	int x, y;
	
	public ArenaPanel(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	@Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        
        Graphics2D g2d = (Graphics2D) g.create();
        String text = x + "," + y;
        FontMetrics fm = g2d.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        g2d.drawString(text, x, y);
        g2d.dispose();
        


    }
}
