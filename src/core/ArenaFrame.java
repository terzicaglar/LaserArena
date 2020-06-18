package core;

import tokens.*;

import javax.swing.*;
import java.awt.*;

class ArenaFrame extends JFrame{

    private static GameMap map;
    private int width = 5;
    private int height = 5;
    private ArenaPanel[][] panels;
    private JPanel[] rowPanels;
    private JPanel upperPanel, noOfTargetsPanel;
    private JPanel[] waitingTokenPanels;

    public ArenaFrame(String title)
    {

        super(title);
        map = new GameMap(width, height);
        rowPanels = new JPanel[map.getHeight()];
        for(int i = 0; i < rowPanels.length; i++){
            rowPanels[i] = new JPanel();
        }
        map2();

        upperPanel = new JPanel();
        waitingTokenPanels = new WaitingListPanel[GameMap.getWaitingTokens().size()];
        for(int i = 0; i < waitingTokenPanels.length; i++)
        {
            waitingTokenPanels[i] = new WaitingListPanel(GameMap.getWaitingTokens().get(i));
            waitingTokenPanels[i].setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            upperPanel.add(waitingTokenPanels[i]);
        }

        noOfTargetsPanel = new JPanel();
        noOfTargetsPanel.add(new JLabel("" + map.getNoOfTargets()));
        noOfTargetsPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        upperPanel.add(noOfTargetsPanel);

        upperPanel.setLayout(new GridLayout(1, waitingTokenPanels.length+1));
        this.add(upperPanel);

        initMap();
        createPanels();
        this.setLayout(new GridLayout(map.getHeight()+1,1));

        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

    }
    //
    private void createPanels() {

        panels = new ArenaPanel[map.getWidth()][map.getHeight()];

        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < panels[i].length; j++) {
                panels[j][i] = new ArenaPanel(this, j, i);
                //panels[j][i].setToolTipText(j + "," + i);
                panels[j][i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                //this.getContentPane().add(panels[j][i]);
                rowPanels[i].add(panels[j][i]);
            }
        }

        for(int i = 0; i < rowPanels.length; i++)
        {
            rowPanels[i].setLayout(new GridLayout(0,map.getWidth()));
            this.add(rowPanels[i]);
        }

        this.setVisible(true);
    }

    private void initMap()
    {
        map.moveBeamsUntilNotMovable();
        //map.print();
        System.out.println("map.checkIfAllWantedTargetsHit(): " + map.checkIfAllWantedTargetsHit());

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
        //map.addWaitingToken(new BlackHole());
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,true, false, false));
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_NORTH,true, false, false));
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_WEST,false, true, false));
        map.addWaitingToken(new BlueMirror(Orientation.BACKSLASH_MIRROR, false, false));
        map.addWaitingToken(new YellowBridge(Orientation.HORIZONTAL_BRIDGE, true, false));
        //map.addWaitingToken(null);

    }

    private void map2()
    {
        map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST, false, true), new Point(2,2));
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,true, false, false));
        map.addWaitingToken(new BlueMirror(Orientation.BACKSLASH_MIRROR, false, false));
        map.addWaitingToken(new YellowBridge(Orientation.HORIZONTAL_BRIDGE, true, false));
        map.addWaitingToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, false, false));
        map.addWaitingToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, false, false));
        map.setNoOfTargets(5);
    }

    private void map3()
    {
        map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST, false, true), new Point(0,3));
        map.addToken(new WhiteObstacle(true), new Point(1,3));
        map.addToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, true, true), new Point(2,3));
        map.addToken(new YellowBridge(Orientation.VERTICAL_BRIDGE, false, true), new Point(3,3));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_EAST,false, true, true), new Point(4,4));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_WEST,false, false, true), new Point(4,0));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_WEST,false, false, true), new Point(1,0));

        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,false, false, false));
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,false, false, false));
        map.addWaitingToken(new BlueMirror(Orientation.BACKSLASH_MIRROR, false, false));
        map.addWaitingToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, false, false));
        map.setNoOfTargets(3);
    }

    private void map4()
    {
        map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST, false, true), new Point(1,2));
        map.addToken(new YellowBridge(Orientation.VERTICAL_BRIDGE, true, true), new Point(2,1));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_EAST,false, false, true), new Point(2,0));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_WEST,false, false, true), new Point(0,4));
        map.addToken(new BlueMirror(Orientation.SLASH_MIRROR, false, true), new Point(4,2));

        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,false, false, false));
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,false, false, false));
        map.addWaitingToken(new PurpleTarget(Orientation.TARGET_ON_EAST,false, false, false));
        map.addWaitingToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, false, false));
        map.addWaitingToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, false, false));
        map.setNoOfTargets(2);
    }


    void refresh(){
        initMap();
        this.repaint();
    }


}
