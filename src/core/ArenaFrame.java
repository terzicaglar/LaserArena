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

    private JMenuBar menuBar;
    private JMenu helpMenu;
    private JMenuItem gameRules;
    private JLabel levelLabel;
    private JButton firstButton, lastButton, prevButton, nextButton/*, ui.solutionButton*/, hintButton;
    private static GameMap map;
    //private int currentLevel;
    //private ArenaPanel[][] panels;
    private JPanel[] rowPanels, waitingTokenPanels;
    private JPanel upperPanel, lowerPanel;
    //private String currentFileName;
    //private Token[][] fileTokens;
    //private ArrayList<Token> fileWaitingList;
    //private int fileNoOfTargets;
    private Game game = Game.getInstance();
    private UIComponents ui = UIComponents.getInstance();

    public ArenaFrame(String title) {
        super(title);
        map = GameMap.getInstance();
        //fileTokens = new Token[MAP_WIDTH][MAP_HEIGHT];
        //fileWaitingList = new ArrayList<>();
        menuBar = new JMenuBar();
        helpMenu = new JMenu("Help");
        gameRules = new JMenuItem("Game Rules");
        gameRules.addActionListener(this);
        helpMenu.add(gameRules);
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);
        game.currentLevel = game.getLastUnlockedLevel();
        Game.gameState = Game.GameState.GAME;
        game.createMapFromFile();
        createAllPanels();
        this.setLayout(new GridLayout(map.getHeight() + 2, 1));

        //TODO:Sizes will be automated not hard-coded
        setSize(500, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

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
        waitingTokenPanels = new WaitingListPanel[map.getWaitingTokens().size() + 1]; //plus one for noOfTargets Panel
        if (waitingTokenPanels.length < MAP_WIDTH)
            panelLength = MAP_WIDTH;
        else
            panelLength = waitingTokenPanels.length;
        for (int i = 0; i < panelLength; i++) {
            if (i < map.getWaitingTokens().size()) //for waiting tokens
            {
                waitingTokenPanels[i] = new WaitingListPanel(map.getWaitingTokens().get(i));
                upperPanel.add(waitingTokenPanels[i]);
            } else if (i == panelLength - 1)//for noOfTargets
            {
                if (map.getNoOfTargets() > 0 && map.getNoOfTargets() <= MAX_WAITING_TOKENS)
                    waitingTokenPanels[map.getWaitingTokens().size()] = new WaitingListPanel(map.getNoOfTargets());
                else
                    throw new IllegalArgumentException("Number of targets must be between 1 and " + MAX_WAITING_TOKENS);
                upperPanel.add(waitingTokenPanels[map.getWaitingTokens().size()]);
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
        levelLabel = new JLabel("Level " + game.currentLevel, SwingConstants.CENTER);
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
        ui.solutionButton = new JButton();
        //Icon icon = new ImageIcon("img/hint.png");
        //TODO: All buttons will have icons
        hintButton = new JButton("Hint");
        hintButton.addActionListener(this);
        if(game.currentLevel <= FIRST_LEVEl)
            prevButton.setEnabled(false);
        else
            prevButton.setEnabled(true);

        if (game.currentLevel == MAX_LEVEL) {
            nextButton.setText("FIN");
        } else {
            nextButton.setText(">");
        }
        if (Game.gameState == Game.GameState.GAME) {
            hintButton.setEnabled(true);
            ui.solutionButton.setText("Solution");
        }
        else if (Game.gameState == Game.GameState.SOLUTION) {
            hintButton.setEnabled(false);
            ui.solutionButton.setText("Play");
        }
        File solutionFile = new File(game.getSolutionFileName());
        //TODO: Correctness of the solution file is not checked
        if (!solutionFile.exists()) {
            ui.solutionButton.setEnabled(false);
            hintButton.setEnabled(false);
        }
        ui.solutionButton.addActionListener(this);

        lowerPanel.add(firstButton);
        lowerPanel.add(prevButton);
        lowerPanel.add(levelLabel);
        lowerPanel.add(nextButton);
        lowerPanel.add(lastButton);
        lowerPanel.add(ui.solutionButton);
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

        ui.panels = new ArenaPanel[map.getWidth()][map.getHeight()];

        for (int i = 0; i < ui.panels.length; i++) {
            for (int j = 0; j < ui.panels[i].length; j++) {
                ui.panels[j][i] = new ArenaPanel(this, j, i);
                rowPanels[i].add(ui.panels[j][i]);
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

    void update() {
        if (map.isLevelFinished()) {
            game.writeToSolutionFile();
            updateLastUnlockedLevelFile();
            hintButton.setEnabled(false);
        }

        if (game.currentLevel < game.getLastUnlockedLevel()) {
            nextButton.setEnabled(true);
        } else {
            nextButton.setEnabled(false);
        }
    }

    private void updateLastUnlockedLevelFile() {
        int lastLevel = game.getLastUnlockedLevel();
        int unlockedLevel = game.currentLevel + 1;
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




    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) //next level
        {
            game.currentLevel++;
            Game.gameState = Game.GameState.GAME;
            refreshAll();
        } else if (e.getSource() == prevButton) //previous level
        {
            game.currentLevel--;
            Game.gameState = Game.GameState.GAME;
            refreshAll();
        } else if (e.getSource() == ui.solutionButton) {
            if (Game.gameState == Game.GameState.GAME) {
                Game.gameState = Game.GameState.SOLUTION;
            } else if (Game.gameState == Game.GameState.SOLUTION) {
                Game.gameState = Game.GameState.GAME;
            }
            refreshAll();
        } else if (e.getSource() == firstButton) {
            game.currentLevel = FIRST_LEVEl;
            Game.gameState = Game.GameState.GAME;
            refreshAll();
        } else if (e.getSource() == lastButton) {
            game.currentLevel = game.getLastUnlockedLevel();
            if (game.currentLevel >= MAX_LEVEL)
                game.currentLevel = MAX_LEVEL;
            Game.gameState = Game.GameState.GAME;
            refreshAll();
        } else if(e.getSource() == gameRules){
            openFileInDesktop("docs/help.pdf");
        } else if( e.getSource() == hintButton)
        {
            game.getHint();
            createAllPanels();
            refresh();
        }
        else{
            throw new IllegalArgumentException();
        }
    }





    public void refreshAll() {
        game.setCurrentFileNameAccToState();
        game.createMapFromFile();
        createAllPanels();
        //refresh();
        this.repaint();
        this.setVisible(true);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (e.getSource() == levelLabel) {
            //left click refreshes the level
            if(e.getButton() == MouseEvent.BUTTON1){
                Game.gameState = Game.GameState.GAME;
            }
            //right click goes to selected level
            else if(e.getButton() == MouseEvent.BUTTON3){
                String goToLevelStr = JOptionPane.showInputDialog(this, "Go To Level");
                int goToLevel = -1;
                if(goToLevelStr != null && !goToLevelStr.equalsIgnoreCase("")){ //it is null if clicked to Cancel Button
                    try{
                        goToLevel = Integer.parseInt(goToLevelStr);
                    }catch (NumberFormatException exc){
                        JOptionPane.showMessageDialog(this,
                                "Invalid Number",
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(goToLevel <= 0 || goToLevel > game.getLastUnlockedLevel()){
                        JOptionPane.showMessageDialog(this,
                                "Level should be between 1 and " + game.getLastUnlockedLevel(),
                                "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        game.currentLevel = goToLevel;
                        Game.gameState = Game.GameState.GAME;
                    }
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
