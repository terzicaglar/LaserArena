package core;

import tokens.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;

import static core.Constants.*;

//TODO: Create a new class Game and migrate game related methods from this class to Game class
//TODO: make comprehensive explanations to all thrown Exceptions

class ArenaFrame extends JFrame implements ActionListener, MouseListener {

    private enum GameState {
        GAME,
        SOLUTION;
    }

    private final String G = "g", G_S = "g-s", G_B = "g-b", B = "b", B_S = "b-s", B_B = "b-b", Y = "y", Y_H = "y-h",
            Y_V = "y-v", R = "r", R_W = "r-w", R_N = "r-n", R_S = "r-s", R_E = "r-e", P = "p", P_W = "p-w",
            P_N = "p-n", P_E = "p-e", P_S = "p-s", PM = "pm", PM_W = "pm-w", PM_N = "pm-n", PM_E = "pm-e",
            PM_S = "pm-s", W = "w"; //shortNames for each Token used for file read/write



    private JMenuBar menuBar;
    private JMenu helpMenu;
    private JMenuItem gameRules;
    private JLabel levelLabel;
    private JButton firstButton, lastButton, prevButton, nextButton, solutionButton, hintButton;
    private static GameMap map;
    private int currentLevel;
    private ArenaPanel[][] panels;
    private JPanel[] rowPanels, waitingTokenPanels;
    private JPanel upperPanel, lowerPanel;
    private String currentFileName;
    private Token[][] fileTokens;
    private ArrayList<Token> fileWaitingList;
    private int fileNoOfTargets;
    private GameState gameState;

    public ArenaFrame(String title) {
        super(title);

        fileTokens = new Token[MAP_WIDTH][MAP_HEIGHT];
        fileWaitingList = new ArrayList<>();
        menuBar = new JMenuBar();
        helpMenu = new JMenu("Help");
        gameRules = new JMenuItem("Game Rules");
        gameRules.addActionListener(this);
        helpMenu.add(gameRules);
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);
        currentLevel = getLastUnlockedLevel();
        gameState = GameState.GAME;
        createMapFromFile();
        createAllPanels();
        this.setLayout(new GridLayout(map.getHeight() + 2, 1));

        //TODO:Sizes will be automated not hard-coded
        setSize(500, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

    }

    private void setCurrentFileNameAccToState() {
        if (gameState == GameState.GAME)
            currentFileName = MAP_LEVEL_PATH + currentLevel + MAP_FILE_EXTENSION;
        else if (gameState == GameState.SOLUTION)
            currentFileName = getSolutionFileName();
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
        waitingTokenPanels = new WaitingListPanel[GameMap.getWaitingTokens().size() + 1]; //plus one for noOfTargets Panel
        if (waitingTokenPanels.length < MAP_WIDTH)
            panelLength = MAP_WIDTH;
        else
            panelLength = waitingTokenPanels.length;
        for (int i = 0; i < panelLength; i++) {
            if (i < GameMap.getWaitingTokens().size()) //for waiting tokens
            {
                waitingTokenPanels[i] = new WaitingListPanel(GameMap.getWaitingTokens().get(i));
                upperPanel.add(waitingTokenPanels[i]);
            } else if (i == panelLength - 1)//for noOfTargets
            {
                if (map.getNoOfTargets() > 0 && map.getNoOfTargets() <= MAX_WAITING_TOKENS)
                    waitingTokenPanels[GameMap.getWaitingTokens().size()] = new WaitingListPanel(map.getNoOfTargets());
                else
                    throw new IllegalArgumentException("Number of targets must be between 1 and " + MAX_WAITING_TOKENS);
                upperPanel.add(waitingTokenPanels[GameMap.getWaitingTokens().size()]);
            } else //empty panel
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
        levelLabel.setToolTipText("Refresh the level / Go to level");
        levelLabel.addMouseListener(this);
        firstButton = new JButton("|<");
        firstButton.addActionListener(this);
        firstButton.setToolTipText("Go to first level");
        lastButton = new JButton(">|");
        lastButton.addActionListener(this);
        lastButton.setToolTipText("Go to last unlocked level");
        prevButton = new JButton("<");
        prevButton.addActionListener(this);
        prevButton.setToolTipText("Go to previous level");
        nextButton = new JButton(">");
        nextButton.setToolTipText("Go to next level");
        nextButton.addActionListener(this);
        solutionButton = new JButton();
        //Icon icon = new ImageIcon("img/hint.png");
        //TODO: All buttons will have icons
        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);
        if(currentLevel <= FIRST_LEVEl)
            prevButton.setEnabled(false);
        else
            prevButton.setEnabled(true);

        if (currentLevel == MAX_LEVEL) {
            nextButton.setText("FIN");
        } else {
            nextButton.setText(">");
        }
        if (gameState == GameState.GAME) {
            hintButton.setEnabled(true);
            solutionButton.setText("Solution");
        }
        else if (gameState == GameState.SOLUTION) {
            hintButton.setEnabled(false);
            solutionButton.setText("Play");
        }
        File solutionFile = new File(getSolutionFileName());
        //TODO: Correctness of the solution file is not checked
        if (!solutionFile.exists()) {
            solutionButton.setEnabled(false);
            hintButton.setEnabled(false);
        }
        solutionButton.addActionListener(this);

        lowerPanel.add(firstButton);
        lowerPanel.add(prevButton);
        lowerPanel.add(levelLabel);
        lowerPanel.add(nextButton);
        lowerPanel.add(lastButton);
        lowerPanel.add(solutionButton);
        lowerPanel.add(hintButton);

        lowerPanel.setLayout(new GridLayout(1, lowerPanel.getComponentCount()));
        this.add(lowerPanel);
    }

    //
    private void createMapPanels() {
        rowPanels = new JPanel[map.getHeight()];
        for (int i = 0; i < rowPanels.length; i++) {
            rowPanels[i] = new JPanel();
        }

        panels = new ArenaPanel[map.getWidth()][map.getHeight()];

        for (int i = 0; i < panels.length; i++) {
            for (int j = 0; j < panels[i].length; j++) {
                panels[j][i] = new ArenaPanel(this, j, i);
                rowPanels[i].add(panels[j][i]);
            }
        }

        for (int i = 0; i < rowPanels.length; i++) {
            rowPanels[i].setLayout(new GridLayout(0, map.getWidth()));
            this.add(rowPanels[i]);
        }

        this.setVisible(true);
    }

    private void updateMap() {
        map.moveBeamsUntilNotMovable();
        update();
    }

    private void openFileInDesktop(String filePath)
    {
        File file = new File(filePath);

        //first check if Desktop is supported by Platform or not
        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) {
            try {
                desktop.open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("File not found");
        }
    }

    private String getSolutionFileName(){
        return MAP_LEVEL_PATH + SOLUTIONS_FOLDER + currentLevel + MAP_FILE_EXTENSION;
    }

    private void readMapFile(String fileName)
    {
        fileTokens = new Token[MAP_WIDTH][MAP_HEIGHT];
        fileWaitingList = new ArrayList<>();
        BufferedReader br = null;
        Token token = null;
        try {
            //TODO: File not found error can be given in all read/write methods, maybe a GUI Warning
            br = new BufferedReader(new FileReader(fileName));

            String line = br.readLine();
            int rowCount = 0;
            while (line != null) {
                //TODO:split method skips last two empty columns for input ,,p,, it does not affect us but good to know
                String[] shortNames = line.split(",");

                if (rowCount < MAP_HEIGHT) {
                    int noOfCommas = line.length() - line.replace(",", "").length();
                    if (noOfCommas < MAP_WIDTH - 1)
                        throw new IllegalArgumentException("At least one comma is missing in " + fileName + " at line " + rowCount);
                }

                int colCount = 0;
                for (String shortName : shortNames) {
                    if (rowCount < MAP_HEIGHT) //tokens inside Map
                    {
                        token = getTokenFromShortName(shortName);
                        if (!shortName.equals("")) {
                            token.setLocationFixed(true); //it is located in map, not waitingList
                            //map.addToken(token, new Point(colCount, rowCount));
                            fileTokens[colCount][rowCount] = token;

                        }
                    } else if (rowCount == MAP_HEIGHT) //waitingList
                    {
                        token = getTokenFromShortName(shortName);
                        //map.addWaitingToken(token);
                        if(token != null) //for empty waiting list
                            fileWaitingList.add(token);
                    } else if (rowCount == MAP_HEIGHT + 1) //noOfTargets
                    {
                        //map.setNoOfTargets(Integer.parseInt(shortName));
                        fileNoOfTargets = Integer.parseInt(shortName);
                    }
                    colCount++;
                }
                line = br.readLine();
                rowCount++;
            }
            if (rowCount <= MAP_HEIGHT + 1)// one line is missing, probably noOfTargets is missed
            {
                throw new IllegalArgumentException("At least one line is missing in " + fileName);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMapFromFile() {
        //GameMap.refresh();
        map = new GameMap(MAP_WIDTH, MAP_HEIGHT);

        //String fileName = MAP_LEVEL_PATH + currentLevel + MAP_FILE_EXTENSION;
        setCurrentFileNameAccToState();
        readMapFile(currentFileName);

        createMap(fileTokens, fileWaitingList, fileNoOfTargets);
    }

    //TODO: All waiting tokens are !orientationFixed (all images in the waiting list are Question Marked), we can fix this later if needed. in the original game they are all Question Mark
    private void createMap(Token[][] inputTokens, ArrayList<Token> inputWaitingList, int inputNoOfTargets){
        for(int i = 0; i < inputTokens.length; i++)
        {
            for(int j = 0; j < inputTokens[i].length; j++)
            {
                if(inputTokens[i][j] != null)
                {
                    map.addToken(inputTokens[i][j], new Point(i, j));
                }
            }
        }

        for(int i = 0; i < inputWaitingList.size(); i++)
            map.addWaitingToken(inputWaitingList.get(i));

        map.setNoOfTargets(inputNoOfTargets);

        if (gameState == GameState.SOLUTION)
            GameMap.setAllWaitingTokensActiveness(false);
    }

    //TODO: This method can be moved to class Token (or its subclasses)
    private Token getTokenFromShortName(String shortName) {
        Token t = null;
        if (shortName.equalsIgnoreCase(G))
            t = new GreenMirror();
        else if (shortName.equalsIgnoreCase(G_S))
            t = new GreenMirror(Orientation.SLASH_MIRROR);
        else if (shortName.equalsIgnoreCase(G_B))
            t = new GreenMirror(Orientation.BACKSLASH_MIRROR);
        else if (shortName.equalsIgnoreCase(B))
            t = new BlueMirror();
        else if (shortName.equalsIgnoreCase(B_S))
            t = new BlueMirror(Orientation.SLASH_MIRROR);
        else if (shortName.equalsIgnoreCase(B_B))
            t = new BlueMirror(Orientation.BACKSLASH_MIRROR);
        else if (shortName.equalsIgnoreCase(Y))
            t = new YellowBridge();
        else if (shortName.equalsIgnoreCase(Y_H))
            t = new YellowBridge(Orientation.HORIZONTAL_BRIDGE);
        else if (shortName.equalsIgnoreCase(Y_V))
            t = new YellowBridge(Orientation.VERTICAL_BRIDGE);
        else if (shortName.equalsIgnoreCase(R))
            t = new RedLaser();
        else if (shortName.equalsIgnoreCase(R_W))
            t = new RedLaser(Orientation.GENERATOR_ON_WEST);
        else if (shortName.equalsIgnoreCase(R_N))
            t = new RedLaser(Orientation.GENERATOR_ON_NORTH);
        else if (shortName.equalsIgnoreCase(R_S))
            t = new RedLaser(Orientation.GENERATOR_ON_SOUTH);
        else if (shortName.equalsIgnoreCase(R_E))
            t = new RedLaser(Orientation.GENERATOR_ON_EAST);
        else if (shortName.equalsIgnoreCase(P))
            t = new PurpleTarget();
        else if (shortName.equalsIgnoreCase(P_W))
            t = new PurpleTarget(Orientation.TARGET_ON_WEST);
        else if (shortName.equalsIgnoreCase(P_N))
            t = new PurpleTarget(Orientation.TARGET_ON_NORTH);
        else if (shortName.equalsIgnoreCase(P_E))
            t = new PurpleTarget(Orientation.TARGET_ON_EAST);
        else if (shortName.equalsIgnoreCase(P_S))
            t = new PurpleTarget(Orientation.TARGET_ON_SOUTH);
        else if (shortName.equalsIgnoreCase(PM))
            t = new PurpleTarget(true);
        else if (shortName.equalsIgnoreCase(PM_W))
            t = new PurpleTarget(Orientation.TARGET_ON_WEST, true);
        else if (shortName.equalsIgnoreCase(PM_N))
            t = new PurpleTarget(Orientation.TARGET_ON_NORTH, true);
        else if (shortName.equalsIgnoreCase(PM_E))
            t = new PurpleTarget(Orientation.TARGET_ON_EAST, true);
        else if (shortName.equalsIgnoreCase(PM_S))
            t = new PurpleTarget(Orientation.TARGET_ON_SOUTH, true);
        else if (shortName.equalsIgnoreCase(W))
            t = new WhiteObstacle();
        else if (shortName.equalsIgnoreCase("")) //null token, i.e., empty cell
            return null;
        if (t == null)
            throw new IllegalArgumentException("Short name \"" + shortName + "\" is not recognized in given file");
        return t;
    }

    private void writeToSolutionFile() {
        //TODO: isOrientationFixed is not considered right now, solution shows the final state only
        String solutionFilePath = getSolutionFileName();
        File solutionFile = new File(solutionFilePath);

        if(!solutionFile.exists()) {
            BufferedWriter bw = null;

            try {
                bw = new BufferedWriter(new FileWriter(solutionFilePath));

                Token t = null;
                String line = "";
                for (int i = 0; i < MAP_HEIGHT; i++) {
                    line = "";
                    for (int j = 0; j < MAP_WIDTH; j++) {
                        t = GameMap.getTokenLocatedInXY(j, i);
                        if (t != null) {
                            line += getShortNameFromToken(t);
                        }
                        if (j != MAP_WIDTH - 1)
                            line += ",";
                        else
                            line += "\n";

                    }
                    bw.write(line);
                }
                //TODO: Tokens in waiting list are written with orientation (e.g., pm-w), if needed it can be converted
                //  to simple form by deleting everything after '-'
                line = "";
                for (int i = 0; i < GameMap.getWaitingTokens().size(); i++) {
                    line += getShortNameFromToken(GameMap.getWaitingTokens().get(i));
                    if (i != GameMap.getWaitingTokens().size() - 1)
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
    }

    private String getShortNameFromToken(Token t) {
        //TODO: Below code can be automated
        if (t instanceof BlueMirror && t.getOrientation() == Orientation.BACKSLASH_MIRROR)
            return B_B;
        else if (t instanceof BlueMirror && t.getOrientation() == Orientation.SLASH_MIRROR)
            return B_S;
        else if (t instanceof GreenMirror && t.getOrientation() == Orientation.BACKSLASH_MIRROR)
            return G_B;
        else if (t instanceof GreenMirror && t.getOrientation() == Orientation.SLASH_MIRROR)
            return G_S;
        else if (t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_EAST && ((PurpleTarget) t).isMandatoryTarget())
            return PM_E;
        else if (t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_WEST && ((PurpleTarget) t).isMandatoryTarget())
            return PM_W;
        else if (t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_NORTH && ((PurpleTarget) t).isMandatoryTarget())
            return PM_N;
        else if (t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_SOUTH && ((PurpleTarget) t).isMandatoryTarget())
            return PM_S;
        else if (t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_EAST && !((PurpleTarget) t).isMandatoryTarget())
            return P_E;
        else if (t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_WEST && !((PurpleTarget) t).isMandatoryTarget())
            return P_W;
        else if (t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_NORTH && !((PurpleTarget) t).isMandatoryTarget())
            return P_N;
        else if (t instanceof PurpleTarget && t.getOrientation() == Orientation.TARGET_ON_SOUTH && !((PurpleTarget) t).isMandatoryTarget())
            return P_S;
        else if (t instanceof YellowBridge && t.getOrientation() == Orientation.HORIZONTAL_BRIDGE)
            return Y_H;
        else if (t instanceof YellowBridge && t.getOrientation() == Orientation.VERTICAL_BRIDGE)
            return Y_V;
        else if (t instanceof RedLaser && t.getOrientation() == Orientation.GENERATOR_ON_WEST)
            return R_W;
        else if (t instanceof RedLaser && t.getOrientation() == Orientation.GENERATOR_ON_NORTH)
            return R_N;
        else if (t instanceof RedLaser && t.getOrientation() == Orientation.GENERATOR_ON_SOUTH)
            return R_S;
        else if (t instanceof RedLaser && t.getOrientation() == Orientation.GENERATOR_ON_EAST)
            return R_E;
        else if (t instanceof WhiteObstacle)
            return W;
        else
            throw new IllegalArgumentException("Token is not recognized");
    }

    void update() {
        if (map.isLevelFinished()) {
            writeToSolutionFile();
            updateLastUnlockedLevelFile();
            hintButton.setEnabled(false);
        }

        if (currentLevel < getLastUnlockedLevel()) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }
    }

    private void updateLastUnlockedLevelFile() {
        int lastLevel = getLastUnlockedLevel();
        int unlockedLevel = currentLevel + 1;
        if (unlockedLevel >= MAX_LEVEL)
            unlockedLevel = MAX_LEVEL;
        if (unlockedLevel > lastLevel) {

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

    void refresh() {
        updateMap();
        this.repaint();
        this.setVisible(true);
    }

    private void getHint()
    {
        //0. read solution file and set fileTokens respectively
        readMapFile(getSolutionFileName());
        boolean exit = false;
        for(int x = 0; x < fileTokens.length && !exit; x++) {
            for(int y = 0; y < fileTokens[x].length && !exit; y++) {
                if(fileTokens[x][y] != null){
                    //1. If map(x,y)(i.e., mapToken) is empty but solutionFile(x,y)(i.e., solToken) is not
                    if( GameMap.getTokenLocatedInXY(x,y) == null){
                        fillEmptyCell(x,y); //Fill empty cell with correct token
                        exit = true;
                    }
                    //2. map(x,y) and solutionFile(x,y) have different type of tokens.
                    else if( !GameMap.getTokenLocatedInXY(x,y).isTokenTypeSameWith(fileTokens[x][y])){
                        //2.a. Move token in map(x,y) to waiting list
                        panels[x][y].cleanPanel(); //Cleans panel and puts necessary token to waiting list
                        fillEmptyCell(x,y); //Fill empty cell with correct token
                        exit = true;
                    }
                    //3. map(x,y) and solutionFile(x,y) have same type of tokens but different orientation
                    else if(GameMap.getTokenLocatedInXY(x,y).isTokenTypeSameWith(fileTokens[x][y]) &&
                            GameMap.getTokenLocatedInXY(x,y).getOrientation() != fileTokens[x][y].getOrientation()){
                        GameMap.getTokenLocatedInXY(x,y).setOrientation(fileTokens[x][y].getOrientation());
                        GameMap.getTokenLocatedInXY(x,y).setLocationFixed(true); //it can be removed, it is done
                        // to be compatible with retrieveTokenFromWaitingList(), where added token cannot move
                        GameMap.getTokenLocatedInXY(x,y).setOrientationFixed(true);
                        exit = true;
                    }

                }
            }
        }
    }

    private void fillEmptyCell(int x, int y)
    {
        boolean moveOn = true, exit = false;
        //1.a. If waiting list has a token which has a class same with solToken, retrieve it
        if( retrieveTokenFromWaitingList(x, y)){
            moveOn = false;
        }

        //1.b. Waiting list does not have a token which has same class with solToken
        // (since exit == false), retrieve this token from map (first delete it from its old
        // location, then put Token into (x,y))
        if(moveOn){
            Token mapToken = null;
            for (int i = 0; i < GameMap.getTokens().length && !exit; i++) {
                for (int j = 0; j < GameMap.getTokens()[i].length && !exit; j++) {
                    mapToken = GameMap.getTokenLocatedInXY(j, i);
                    //retrieve token from map where it is placed wrong (!mapToken.isTokenTypeSameWith(fileTokens[j][i])
                    if(mapToken != null &&
                            !mapToken.isLocationFixed() && mapToken.isTokenTypeSameWith(fileTokens[x][y]) &&
                            !mapToken.isTokenTypeSameWith(fileTokens[j][i])){
                        panels[j][i].cleanPanel(); //cleans panel and puts necessary token to waiting list
                        retrieveTokenFromWaitingList(x, y); //retrieve that token from waiting list
                        exit = true;
                        break;
                    }
                }
            }
        }

    }

    private boolean retrieveTokenFromWaitingList(int x, int y) {
        Token waitingToken;
        for(int i = 0; i < GameMap.getActiveWaitingTokens().size(); i++){
            waitingToken = GameMap.getActiveWaitingTokens().get(i);
            if(waitingToken.isTokenTypeSameWith(fileTokens[x][y])){
                map.addToken(fileTokens[x][y], new Point(x, y)); //hinted tokens cannot be moved, since
                // clickCount and prevToken in ArenaPanel malfunctions
                map.removeWaitingToken(waitingToken);
                return true;
            }
        }
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) //next level
        {
            currentLevel++;
            gameState = GameState.GAME;
            refreshAll();
        } else if (e.getSource() == prevButton) //previous level
        {
            currentLevel--;
            gameState = GameState.GAME;
            refreshAll();
        } else if (e.getSource() == solutionButton) {
            if (gameState == GameState.GAME) {
                gameState = GameState.SOLUTION;
            } else if (gameState == GameState.SOLUTION) {
                gameState = GameState.GAME;
            }
            refreshAll();
        } else if (e.getSource() == firstButton) {
            currentLevel = FIRST_LEVEl;
            gameState = GameState.GAME;
            refreshAll();
        } else if (e.getSource() == lastButton) {
            currentLevel = getLastUnlockedLevel();
            if (currentLevel >= MAX_LEVEL)
                currentLevel = MAX_LEVEL;
            gameState = GameState.GAME;
            refreshAll();
        } else if(e.getSource() == gameRules){
            openFileInDesktop("docs/help.pdf");
        } else if( e.getSource() == hintButton)
        {
            getHint();
            createAllPanels();
            refresh();
        }
        else{
            throw new IllegalArgumentException();
        }
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

    public void refreshAll() {
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

        if (e.getSource() == levelLabel) {
            //left click refreshes the level
            if(e.getButton() == MouseEvent.BUTTON1){
                gameState = GameState.GAME;
            }
            //right click goes to selected level
            else if(e.getButton() == MouseEvent.BUTTON3){
                String goToLevelStr = JOptionPane.showInputDialog(this, "Go To Level");
                int goToLevel = -1;
                if(goToLevelStr != null){ //it is null if clicked to Cancel Button
                    try{
                        goToLevel = Integer.parseInt(goToLevelStr);
                    }catch (NumberFormatException exc){
                        JOptionPane.showMessageDialog(this,
                                "Invalid Numver",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
                if(goToLevel <= 0 || goToLevel > getLastUnlockedLevel()){
                    JOptionPane.showMessageDialog(this,
                            "Level should be between 1 and " + getLastUnlockedLevel(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                else{
                    currentLevel = goToLevel;
                    gameState = GameState.GAME;
                }
            }
        }
        refreshAll();
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
