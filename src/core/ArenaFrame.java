package core;

import tokens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

//TODO: make comprehensive explanations to all thrown Exceptions
//TODO: infinite loop laser beam is given in infiniteLoopLaserBeam.png
class ArenaFrame extends JFrame implements ActionListener, MouseListener {

    private JLabel levelLabel;
    private JButton firstButton, lastButton;

    private enum GameState{
        GAME,
        SOLUTION;
    }

    private final String G = "g", G_S = "g-s", G_B = "g-b", B = "b", B_S = "b-s", B_B = "b-b", Y = "y", Y_H = "y-h",
        Y_V = "y-v", R = "r", R_W = "r-w", R_N = "r-n", R_S = "r-s", R_E = "r-e", P = "p", P_W = "p-w",
        P_N = "p-n", P_E = "p-e", P_S = "p-s", PM = "pm", PM_W = "pm-w", PM_N = "pm-n", PM_E = "pm-e",
        PM_S = "pm-s", W = "w"; //shortNames for each Token used for file read/write
    private final int MAX_WAITING_TOKENS = 5, MAX_LEVEL = 66;
    private final String MAP_FILE_EXTENSION = ".csv", MAP_LEVEL_PATH = "levels/",
            SOLUTIONS_FOLDER = "solutions/", LAST_LEVEL_FILE ="LastUnlockedLevel.txt";

    private static GameMap map;
    private int width = 5, height = 5, firstLevel = 61, currentLevel;
    private ArenaPanel[][] panels;
    private JPanel[] rowPanels;
    private JPanel upperPanel, lowerPanel;
    private JPanel[] waitingTokenPanels;
    private JButton prevButton, nextButton, solutionButton;
    private String currentFileName;
    private GameState gameState;

    public ArenaFrame(String title)
    {
        super(title);
        currentLevel = getLastUnlockedLevel();
        gameState = GameState.GAME;
        createMapFromFile();
        createAllPanels();
        this.setLayout(new GridLayout(map.getHeight()+2,1));

        //TODO:Sizes will be automated not hard-coded
        setSize(500,700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

    }

    private void setCurrentFileNameAccToState()
    {
        if(gameState == GameState.GAME)
            currentFileName = MAP_LEVEL_PATH + currentLevel + MAP_FILE_EXTENSION;
        else if( gameState == GameState.SOLUTION)
            currentFileName = MAP_LEVEL_PATH + SOLUTIONS_FOLDER + currentLevel + MAP_FILE_EXTENSION;
    }

    private void createAllPanels() {
        this.getContentPane().removeAll(); //very important since panels are not removed when leveled up
        createUpperPanel();
        createMapPanels();
        createLowerPanel();
        updateMap();
    }

    private void createUpperPanel() {
        upperPanel = new JPanel();
        int panelLength = 0;
        waitingTokenPanels = new WaitingListPanel[GameMap.getWaitingTokens().size()+1]; //plus one for noOfTargets Panel
        if(waitingTokenPanels.length < width)
            panelLength = width;
        else
            panelLength = waitingTokenPanels.length;
        for(int i = 0; i < panelLength; i++)
        {
            if(i < GameMap.getWaitingTokens().size()) //for waiting tokens
            {
                waitingTokenPanels[i] = new WaitingListPanel(GameMap.getWaitingTokens().get(i));
                upperPanel.add(waitingTokenPanels[i]);
            }
            else if(i == panelLength-1)//for noOfTargets
            {
                if( map.getNoOfTargets() > 0 && map.getNoOfTargets() <= MAX_WAITING_TOKENS)
                    waitingTokenPanels[GameMap.getWaitingTokens().size()] = new WaitingListPanel(map.getNoOfTargets());
                else
                    throw new IllegalArgumentException("Number of targets must be between 1 and " + MAX_WAITING_TOKENS);
                upperPanel.add(waitingTokenPanels[GameMap.getWaitingTokens().size()]);
            }
            else //empty panel
            {
                upperPanel.add(new JPanel());
            }

        }
        upperPanel.setLayout(new GridLayout(1, waitingTokenPanels.length));
        this.add(upperPanel);
    }

    private void createLowerPanel() {
        lowerPanel = new JPanel();
        levelLabel = new JLabel("Level " + currentLevel, SwingConstants.CENTER);
        levelLabel.addMouseListener(this);
        firstButton = new JButton("<<");
        firstButton.addActionListener(this);
        lastButton = new JButton(">>");
        lastButton.addActionListener(this);
        prevButton = new JButton("<");
        prevButton.addActionListener(this);
        nextButton = new JButton(">");
        //nextButton.setEnabled(false);
        nextButton.addActionListener(this);
        solutionButton = new JButton();

        if(currentLevel == MAX_LEVEL)
        {
            nextButton.setText("MAX LEVEL");
        }
        else
        {
            nextButton.setText(">");
        }

        if(gameState == GameState.GAME)
            solutionButton.setText("Solution");
        else if(gameState == GameState.SOLUTION)
            solutionButton.setText("Return to Game");
        File solutionFile = new File( MAP_LEVEL_PATH + SOLUTIONS_FOLDER + currentLevel + MAP_FILE_EXTENSION);
        //TODO: Correctness of the solution file is not checked
        if(!solutionFile.exists())
            solutionButton.setEnabled(false);
        solutionButton.addActionListener(this);

        if(currentLevel < 2)
            prevButton.setEnabled(false);

        lowerPanel.add(firstButton);
        lowerPanel.add(prevButton);
        lowerPanel.add(levelLabel);
        lowerPanel.add(nextButton);
        lowerPanel.add(lastButton);
        lowerPanel.add(solutionButton);

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
        update();
        map.moveBeamsUntilNotMovable();
    }


    private void createMapFromFile()
    {
        //GameMap.refresh();
        map = new GameMap(width, height);
        //String fileName = MAP_LEVEL_PATH + currentLevel + MAP_FILE_EXTENSION;
        setCurrentFileNameAccToState();
        BufferedReader br = null;
        Token token = null;
        try {
            br = new BufferedReader(new FileReader(currentFileName));

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
                        throw new IllegalArgumentException("At least one comma is missing in " + currentFileName + " at line " + rowCount);
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
                throw new IllegalArgumentException("At least one line is missing in " + currentFileName);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(gameState == GameState.SOLUTION)
            GameMap.setAllWaitingTokensActiveness(false);

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
    private Token getTokenFromShortName(String shortName)
    {
        Token t = null;
        if(shortName.equalsIgnoreCase(G))
            t = new GreenMirror();
        else if(shortName.equalsIgnoreCase(G_S))
            t = new GreenMirror(Orientation.SLASH_MIRROR);
        else if(shortName.equalsIgnoreCase(G_B))
            t = new GreenMirror(Orientation.BACKSLASH_MIRROR);
        else if(shortName.equalsIgnoreCase(B))
            t = new BlueMirror();
        else if(shortName.equalsIgnoreCase(B_S))
            t = new BlueMirror(Orientation.SLASH_MIRROR);
        else if(shortName.equalsIgnoreCase(B_B))
            t = new BlueMirror(Orientation.BACKSLASH_MIRROR);
        else if(shortName.equalsIgnoreCase(Y))
            t = new YellowBridge();
        else if(shortName.equalsIgnoreCase(Y_H))
            t = new YellowBridge(Orientation.HORIZONTAL_BRIDGE);
        else if(shortName.equalsIgnoreCase(Y_V))
            t = new YellowBridge(Orientation.VERTICAL_BRIDGE);
        else if(shortName.equalsIgnoreCase(R))
            t = new RedLaser();
        else if(shortName.equalsIgnoreCase(R_W))
            t = new RedLaser(Orientation.GENERATOR_ON_WEST);
        else if(shortName.equalsIgnoreCase(R_N))
            t = new RedLaser(Orientation.GENERATOR_ON_NORTH);
        else if(shortName.equalsIgnoreCase(R_S))
            t = new RedLaser(Orientation.GENERATOR_ON_SOUTH);
        else if(shortName.equalsIgnoreCase(R_E))
            t = new RedLaser(Orientation.GENERATOR_ON_EAST);
        else if(shortName.equalsIgnoreCase(P))
            t = new PurpleTarget();
        else if(shortName.equalsIgnoreCase(P_W))
            t = new PurpleTarget(Orientation.TARGET_ON_WEST);
        else if(shortName.equalsIgnoreCase(P_N))
            t = new PurpleTarget(Orientation.TARGET_ON_NORTH);
        else if(shortName.equalsIgnoreCase(P_E))
            t = new PurpleTarget(Orientation.TARGET_ON_EAST);
        else if(shortName.equalsIgnoreCase(P_S))
            t = new PurpleTarget(Orientation.TARGET_ON_SOUTH);
        else if(shortName.equalsIgnoreCase(PM))
            t = new PurpleTarget(true);
        else if(shortName.equalsIgnoreCase(PM_W))
            t = new PurpleTarget(Orientation.TARGET_ON_WEST, true);
        else if(shortName.equalsIgnoreCase(PM_N))
            t = new PurpleTarget(Orientation.TARGET_ON_NORTH, true);
        else if(shortName.equalsIgnoreCase(PM_E))
            t = new PurpleTarget(Orientation.TARGET_ON_EAST, true);
        else if(shortName.equalsIgnoreCase(PM_S))
            t = new PurpleTarget(Orientation.TARGET_ON_SOUTH, true);
        else if(shortName.equalsIgnoreCase(W))
            t = new WhiteObstacle();
        else if(shortName.equalsIgnoreCase("")) //null token, i.e., empty cell
            return null;
        if(t == null)
            throw new IllegalArgumentException("Short name \"" + shortName + "\" is not recognized in given file");
        return t;
    }

    private void writeToSolutionFile()
    {
        //TODO: isOrientationFixed is not considered right now, solution shows the final state only
        //TODO: upper and lower panels have not considered yet

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(MAP_LEVEL_PATH + SOLUTIONS_FOLDER + currentLevel + MAP_FILE_EXTENSION));

            Token t = null;
            String line = "";
            for(int i = 0; i < height; i++)
            {
                line = "";
                for(int j = 0; j < width; j++)
                {
                    t = GameMap.getTokenLocatedInXY(j, i);
                    if(t != null)
                    {
                        line += getShortNameFromToken(t);
                    }
                    if( j != width - 1)
                        line += ",";
                    else
                        line += "\n";

                }
                bw.write(line);
            }
            //TODO: Tokens in waiting list are written with orientation (e.g., pm-w), if needed it can be converted
            //  to simple form by deleting everything after '-'
            line = "";
            for(int i = 0; i < GameMap.getWaitingTokens().size(); i++)
            {
                line += getShortNameFromToken(GameMap.getWaitingTokens().get(i));
                if(i != GameMap.getWaitingTokens().size() - 1)
                    line += ",";
                else
                    line += "\n";
            }
            bw.write(line);
            line = GameMap.getNoOfTargets() + "\n";
            bw.write(line);

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        solutionButton.setEnabled(true);
    }

    private String getShortNameFromToken(Token t)
    {
        //TODO: Below code can be automated
        if(t instanceof BlueMirror && t.getOrientation() == Orientation.BACKSLASH_MIRROR)
            return B_B;
        else if(t instanceof BlueMirror && t.getOrientation() == Orientation.SLASH_MIRROR)
            return B_S;
        else if(t instanceof GreenMirror && t.getOrientation() == Orientation.BACKSLASH_MIRROR)
            return G_B;
        else if(t instanceof GreenMirror && t.getOrientation() == Orientation.SLASH_MIRROR)
            return G_S;
        else if(t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_EAST && ((PurpleTarget) t).isMandatoryTarget())
            return PM_E;
        else if(t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_WEST && ((PurpleTarget) t).isMandatoryTarget())
            return PM_W;
        else if(t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_NORTH && ((PurpleTarget) t).isMandatoryTarget())
            return PM_N;
        else if(t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_SOUTH && ((PurpleTarget) t).isMandatoryTarget())
            return PM_S;
        else if(t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_EAST && !((PurpleTarget) t).isMandatoryTarget())
            return P_E;
        else if(t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_WEST && !((PurpleTarget) t).isMandatoryTarget())
            return P_W;
        else if(t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_NORTH && !((PurpleTarget) t).isMandatoryTarget())
            return P_N;
        else if(t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_SOUTH && !((PurpleTarget) t).isMandatoryTarget())
            return P_S;
        else if(t instanceof YellowBridge && t.getOrientation() == Orientation.HORIZONTAL_BRIDGE)
            return Y_H;
        else if(t instanceof YellowBridge && t.getOrientation() == Orientation.VERTICAL_BRIDGE)
            return Y_V;
        else if(t instanceof RedLaser && t.getOrientation() == Orientation.GENERATOR_ON_WEST)
            return R_W;
        else if(t instanceof RedLaser && t.getOrientation() == Orientation.GENERATOR_ON_NORTH)
            return R_N;
        else if(t instanceof RedLaser && t.getOrientation() == Orientation.GENERATOR_ON_SOUTH)
            return R_S;
        else if(t instanceof RedLaser && t.getOrientation() == Orientation.GENERATOR_ON_EAST)
            return R_E;
        else if(t instanceof WhiteObstacle)
            return W;
        else
            throw new IllegalArgumentException("Token is not recognized");
    }

    void update()
    {
        if(map.isLevelFinished())
        {
            writeToSolutionFile();
            updateLastUnlockedLevelFile();
        }

        if(currentLevel < getLastUnlockedLevel())
        {
            nextButton.setEnabled(true);
        }
        else
        {
            nextButton.setEnabled(false);
        }
    }

    private void updateLastUnlockedLevelFile() {
        int lastLevel = getLastUnlockedLevel();
        int unlockedLevel = currentLevel + 1;
        if(unlockedLevel >= MAX_LEVEL)
            unlockedLevel = MAX_LEVEL;
        if(unlockedLevel > lastLevel) {

            BufferedWriter bw = null;

            try {
                bw = new BufferedWriter(new FileWriter(MAP_LEVEL_PATH + LAST_LEVEL_FILE));
                bw.write("" + unlockedLevel);
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void refresh(){
        updateMap();
        this.repaint();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nextButton) //next level
        {
            currentLevel++;
            gameState = GameState.GAME;
        }
        else if(e.getSource() == prevButton) //previous level
        {
            currentLevel--;
            gameState = GameState.GAME;
        }
        else if(e.getSource() == solutionButton)
        {
            if (gameState == GameState.GAME) {
                gameState = GameState.SOLUTION;
            }
            else if (gameState == GameState.SOLUTION) {
                gameState = GameState.GAME;
            }
        }
        else if(e.getSource() == firstButton)
        {
            currentLevel = firstLevel;
            gameState = GameState.GAME;
        }
        else if(e.getSource() == lastButton)
        {
            currentLevel = getLastUnlockedLevel();
            if(currentLevel >= MAX_LEVEL)
                currentLevel = MAX_LEVEL;
            gameState = GameState.GAME;
        }
        else
        {
            throw new IllegalArgumentException();
        }
        refreshAll();
    }

    private int getLastUnlockedLevel() {
        BufferedReader br = null;
        int lastLevel = -1;
        try {
            br = new BufferedReader(new FileReader(MAP_LEVEL_PATH + LAST_LEVEL_FILE));
            lastLevel = Integer.parseInt(br.readLine());
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastLevel;
    }

    public void refreshAll()
    {
        setCurrentFileNameAccToState();
        createMapFromFile();
        createAllPanels();
        refresh();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getSource() == levelLabel)
        {
            gameState = GameState.GAME;
            refreshAll();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
