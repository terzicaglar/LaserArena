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
    //
    private void createPanels() {
        panels = new ArenaPanel[width][height];

        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < panels[i].length; j++) {
                panels[j][i] = new ArenaPanel(this, j, i);
                //panels[j][i].setToolTipText(j + "," + i);
                panels[j][i].setBorder(BorderFactory.createLineBorder(Color.black));
                this.getContentPane().add(panels[j][i]);
            }
        }
    }

    private void initMap()
    {
        map.moveBeamsUntilNotMovable();
        //map.print();
        System.out.println("map.checkIfAllWantedTargetsHit(): " + map.checkIfAllWantedTargetsHit());
        createPanels();
    }

    private void map1()
    {
        map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST, false, true), new Point(0,3));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_EAST,true, false, true), new Point(0,0));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_SOUTH, false, false, true), new Point(0,2));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_SOUTH, false, true, true), new Point(3,4));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_WEST,false, true, true), new Point(4,3));
        map.addToken(new BlueMirror(Orientation.SLASH_MIRROR, false, true), new Point(4,4));
        map.addToken(new BlueMirror(Orientation.BACKSLASH_MIRROR, false, true), new Point(2,2));
        map.addToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, true, true), new Point(3,3));
        map.addToken(new GreenMirror(Orientation.SLASH_MIRROR, false, true), new Point(2,3));
        map.addToken(new YellowBridge(Orientation.HORIZONTAL_BRIDGE, true, true), new Point(0,1));
        map.addToken(new YellowBridge(Orientation.VERTICAL_BRIDGE, false, true), new Point(1,3));
        //map.addToken(new WhiteObstacle(true), new Point(4,0));
        map.addToken(new WhiteObstacle(true), new Point(1,2));
        map.addWaitingToken(new BlackHole());
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,true, false, false));
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,true, false, false));
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,false, true, false));
        map.addWaitingToken(new BlueMirror(Orientation.BACKSLASH_MIRROR, false, false));
        map.addWaitingToken(new YellowBridge(Orientation.HORIZONTAL_BRIDGE, true, false));
        //map.addWaitingToken(null);

    }

    private void map2()
    {
        map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST, false, true), new Point(2,2));
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,true, false, false));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button1)
        {
            refresh();
        }
    }

    protected void refresh(){
        initMap();
        this.repaint();
    }


}
