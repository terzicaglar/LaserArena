package core;

import javax.swing.*;

public class UIComponents {
    public JButton solutionButton;
    public ArenaPanel[][] panels;
    private static UIComponents instance = null;

    private UIComponents(){}

    public static UIComponents getInstance(){
        if(instance == null)
            instance = new UIComponents();
        return instance;
    }
}
