package core;

import tokens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArenaFrame extends JFrame implements ActionListener {

    private JButton button1;
    static GameMap map;
    int width = 5, height = 5, noOftargets = 1;
    ArenaPanel[][] panels;
    LaserBeam lb;

    public ArenaFrame(String title)
    {
        super(title);
        map = new GameMap(width, height, noOftargets);
        map1();
        initMap();
        button1 = new JButton("refresh");
        this.setLayout(new GridLayout(map.getHeight()+1, map.getWidth()));
        add(button1);

        button1.addActionListener(this);
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

    }

    private void createPanels() {
        panels = new ArenaPanel[width][height];

        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < panels[i].length; j++) {
                panels[j][i] = new ArenaPanel(j, i);
                //panels[j][i].setToolTipText(j + "," + i);
                panels[j][i].setBorder(BorderFactory.createLineBorder(Color.black));
                this.getContentPane().add(panels[j][i]);
            }
        }
    }

    public void initMap()
    {
        map.moveBeamsUntilNotMovable();
        //map.print();
        System.out.println("map.checkIfAllWantedTargetsHit(): " + map.checkIfAllWantedTargetsHit());
        createPanels();
    }

    public void map1()
    {
        map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST, true), new Point(0,3));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_EAST,true, false), new Point(0,0));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_SOUTH, false, false), new Point(0,2));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_SOUTH, false, true), new Point(3,4));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_WEST,false, true), new Point(4,3));
        map.addToken(new BlueMirror(Orientation.SLASH_MIRROR, false), new Point(4,4));
        map.addToken(new BlueMirror(Orientation.BACKSLASH_MIRROR, false), new Point(2,2));
        map.addToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, true), new Point(3,3));
        map.addToken(new GreenMirror(Orientation.SLASH_MIRROR, false), new Point(2,3));
        map.addToken(new YellowBridge(Orientation.HORIZONTAL_BRIDGE, true), new Point(0,1));
        map.addToken(new YellowBridge(Orientation.VERTICAL_BRIDGE, false), new Point(1,3));
        map.addToken(new WhiteObstacle(), new Point(4,0));
        map.addToken(new WhiteObstacle(), new Point(1,2));

    }

    public void map2()
    {
        map.addToken(new GreenMirror(Orientation.SLASH_MIRROR, false), new Point(1,3));
        map.addToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, false), new Point(2,3));
        map.addToken(new GreenMirror(Orientation.SLASH_MIRROR, false), new Point(3,3));
        map.addToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, false), new Point(4,3));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button1)
        {
            //TODO: RedLaser orientation change works but mirror etc. orientation change does not work
            initMap();
            this.repaint();
        }
    }
}
