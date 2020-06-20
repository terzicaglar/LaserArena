package core;

import tokens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

//TODO: make comprehensive explanations to all thrown Exceptions

class ArenaFrame extends JFrame implements ActionListener {
    private final String MAP_FILE_EXTENSION = ".csv";

    private static GameMap map;
    private int width = 5, height = 5, currentLevel = 1;
    private ArenaPanel[][] panels;
    private JPanel[] rowPanels;
    private JPanel upperPanel, lowerPanel;
    private JPanel[] waitingTokenPanels;
    private JButton prevButton, nextButton;

    public ArenaFrame(String title)
    {
        super(title);
        createMapFromFile();
        createAllPanels();
        this.setLayout(new GridLayout(map.getHeight()+2,1));

        //TODO:Sizes will be automated not hard-coded
        setSize(500,700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

    }

    private void createAllPanels() {
        this.getContentPane().removeAll(); //very important since panels are not removed when leveled up
        createUpperPanel();
        updateMap();
        createMapPanels();
        createLowerPanel();
    }

    private void createUpperPanel() {
        upperPanel = new JPanel();
        waitingTokenPanels = new WaitingListPanel[GameMap.getWaitingTokens().size()+1]; //plus one for noOfTargets Panel
        for(int i = 0; i < waitingTokenPanels.length; i++)
        {
            if(i < GameMap.getWaitingTokens().size()) //for waiting tokens
            {
                waitingTokenPanels[i] = new WaitingListPanel(GameMap.getWaitingTokens().get(i));
                //waitingTokenPanels[i].setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
            }
            else //for noOfTargets
            {
                if( map.getNoOfTargets() > 0 && map.getNoOfTargets() < 6)
                    waitingTokenPanels[i] = new WaitingListPanel(map.getNoOfTargets());
                else
                    throw new IllegalArgumentException("Number of targets must be between 1 and 5");
            }
            upperPanel.add(waitingTokenPanels[i]);
        }
        upperPanel.setLayout(new GridLayout(1, waitingTokenPanels.length));
        this.add(upperPanel);
    }

    private void createLowerPanel() {
        lowerPanel = new JPanel();
        JLabel levelLabel = new JLabel("Level " + currentLevel, SwingConstants.CENTER);
        prevButton = new JButton("<=");
        prevButton.addActionListener(this);
        nextButton = new JButton("=>");
        nextButton.setEnabled(false);
        nextButton.addActionListener(this);

        if(currentLevel < 2)
            prevButton.setEnabled(false);

        lowerPanel.add(prevButton);
        lowerPanel.add(levelLabel);
        lowerPanel.add(nextButton);

        lowerPanel.setLayout(new GridLayout(1, lowerPanel.getComponentCount()));
        this.add(lowerPanel);
    }

    //
    private void createMapPanels() {
        rowPanels = new JPanel[map.getHeight()];
        for(int i = 0; i < rowPanels.length; i++){
            rowPanels[i] = new JPanel();
        }

        panels = new ArenaPanel[map.getWidth()][map.getHeight()];

        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < panels[i].length; j++) {
                panels[j][i] = new ArenaPanel(this, j, i);
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

    private void updateMap()
    {
        map.moveBeamsUntilNotMovable();
        //TODO: If map.isGameFinished() returns true we will save the solution to solutions folder for that level
        System.out.println("map.isGameFinished(): " + map.isGameFinished());
    }


    private void createMapFromFile()
    {
        //GameMap.refresh();
        map = new GameMap(width, height);
        String fileName = "levels/bonus/" + currentLevel + MAP_FILE_EXTENSION;
        BufferedReader br = null;
        Token token = null;
        try {
            br = new BufferedReader(new FileReader(fileName));

            String line = br.readLine();
            int rowCount = 0;
            while(line != null)
            {
                //TODO:split method skips last two empty columns for input ,,p,, it does not affect us but good to know
                String[] shortNames = line.split(",");

                if(rowCount < height)
                {
                    int noOfCommas = line.length() - line.replace(",", "").length();
                    if(noOfCommas < width - 1)
                        throw new IllegalArgumentException("At least one comma is missing in " + fileName + " at line " + rowCount);
                }

                int colCount = 0;
                for(String shortName: shortNames) {
                    if (rowCount < height) //tokens inside Map
                    {
                        token = getTokenFromShortName(shortName);
                        if(!shortName.equals(""))
                        {
                            token.setLocationFixed(true); //it is located in map, not waitingList
                            map.addToken(token, new Point(colCount, rowCount));

                        }
                    }
                    else if(rowCount == height) //waitingList
                    {
                        token = getTokenFromShortName(shortName);
                        map.addWaitingToken(token);
                    }
                    else if(rowCount == height + 1) //noOfTargets
                    {
                        map.setNoOfTargets(Integer.parseInt(shortName));
                    }
                    colCount ++;
                }
                line = br.readLine();
                rowCount++;
            }
            if(rowCount <= height + 1)// one line is missing, probably noOfTargets is missed
            {
                throw new IllegalArgumentException("At least one line is missing in " + fileName);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //TODO: All waiting tokens are !orientationFixed (all images in the waiting list are Question Marked), we can fix this later if needed. in the original game they are all Question Mark
    private void map1()
    {
        map = new GameMap(width, height);
        map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST, false, true), new Point(0,3));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_EAST,true, false, true), new Point(0,0));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_SOUTH, false, false, true), new Point(0,2));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_SOUTH, false, true, true), new Point(3,4));
        map.addToken(new PurpleTarget(Orientation.TARGET_ON_WEST,false, true, true), new Point(4,3));
        map.addToken(new BlueMirror(Orientation.SLASH_MIRROR, false, true), new Point(4,4));
        map.addToken(new BlueMirror(Orientation.BACKSLASH_MIRROR, false, true), new Point(2,2));
        map.addToken(new GreenMirror(Orientation.BACKSLASH_MIRROR, false, true), new Point(3,3));
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
        map = new GameMap(width, height);
        map.addToken(new RedLaser(Orientation.GENERATOR_ON_EAST, false, true), new Point(2,2));

        //map.addWaitingToken(new RedLaser());
        map.addWaitingToken(new PurpleTarget());
        map.addWaitingToken(new PurpleTarget(true));
        //map.addWaitingToken(new PurpleTarget(false));
        map.addWaitingToken(new BlueMirror());
        map.addWaitingToken(new YellowBridge());
        map.addWaitingToken(new GreenMirror());
        //map.addWaitingToken(new GreenMirror());
        map.addWaitingToken(new WhiteObstacle());
        map.setNoOfTargets(1);
    }

    private void map3()
    {
        map = new GameMap(width, height);
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
        map = new GameMap(width, height);
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


    //TODO: This method can be moved to class Token (or its subclasses)
    public Token getTokenFromShortName(String shortName)
    {
        Token t = null;
        if(shortName.equalsIgnoreCase("g"))
            t = new GreenMirror();
        else if(shortName.equalsIgnoreCase("g-s"))
            t = new GreenMirror(Orientation.SLASH_MIRROR);
        else if(shortName.equalsIgnoreCase("g-b"))
            t = new GreenMirror(Orientation.BACKSLASH_MIRROR);
        else if(shortName.equalsIgnoreCase("b"))
            t = new BlueMirror();
        else if(shortName.equalsIgnoreCase("b-s"))
            t = new BlueMirror(Orientation.SLASH_MIRROR);
        else if(shortName.equalsIgnoreCase("b-b"))
            t = new BlueMirror(Orientation.BACKSLASH_MIRROR);
        else if(shortName.equalsIgnoreCase("y"))
            t = new YellowBridge();
        else if(shortName.equalsIgnoreCase("y-h"))
            t = new YellowBridge(Orientation.HORIZONTAL_BRIDGE);
        else if(shortName.equalsIgnoreCase("y-v"))
            t = new YellowBridge(Orientation.VERTICAL_BRIDGE);
        else if(shortName.equalsIgnoreCase("r"))
            t = new RedLaser();
        else if(shortName.equalsIgnoreCase("r-gw"))
            t = new RedLaser(Orientation.GENERATOR_ON_WEST);
        else if(shortName.equalsIgnoreCase("r-gn"))
            t = new RedLaser(Orientation.GENERATOR_ON_NORTH);
        else if(shortName.equalsIgnoreCase("r-gs"))
            t = new RedLaser(Orientation.GENERATOR_ON_SOUTH);
        else if(shortName.equalsIgnoreCase("r-ge"))
            t = new RedLaser(Orientation.GENERATOR_ON_EAST);
        else if(shortName.equalsIgnoreCase("p"))
            t = new PurpleTarget();
        else if(shortName.equalsIgnoreCase("p-tw"))
            t = new PurpleTarget(Orientation.TARGET_ON_WEST);
        else if(shortName.equalsIgnoreCase("p-tn"))
            t = new PurpleTarget(Orientation.TARGET_ON_NORTH);
        else if(shortName.equalsIgnoreCase("p-te"))
            t = new PurpleTarget(Orientation.TARGET_ON_EAST);
        else if(shortName.equalsIgnoreCase("p-ts"))
            t = new PurpleTarget(Orientation.TARGET_ON_SOUTH);
        else if(shortName.equalsIgnoreCase("pm"))
            t = new PurpleTarget(true);
        else if(shortName.equalsIgnoreCase("pm-tw"))
            t = new PurpleTarget(Orientation.TARGET_ON_WEST, true);
        else if(shortName.equalsIgnoreCase("pm-tn"))
            t = new PurpleTarget(Orientation.TARGET_ON_NORTH, true);
        else if(shortName.equalsIgnoreCase("pm-te"))
            t = new PurpleTarget(Orientation.TARGET_ON_EAST, true);
        else if(shortName.equalsIgnoreCase("pm-ts"))
            t = new PurpleTarget(Orientation.TARGET_ON_SOUTH, true);
        else if(shortName.equalsIgnoreCase("w"))
            t = new WhiteObstacle();
        else if(shortName.equalsIgnoreCase("")) //null token, i.e., empty cell
            return null;
        if(t == null)
            throw new IllegalArgumentException("Short name \"" + shortName + "\" is not recognized in given file");
        return t;
    }

    void update()
    {
        if(map.isGameFinished())
        {
            //TODO: nextButton will be disabled when entered a new level
            nextButton.setEnabled(true);
        }
        else
        {
            nextButton.setEnabled(false);
        }
    }

    void refresh(){
        updateMap();
        update();
        this.repaint();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nextButton) //next level
        {
            currentLevel++;

        }
        else if(e.getSource() == prevButton) //previous level
        {
            currentLevel--;
        }
        else
        {
            throw new IllegalArgumentException();
        }
        createMapFromFile();
        createAllPanels();
        refresh();
    }
}
