package game.componenets;

import game.GameState;

import javax.swing.*;
import java.awt.*;

/**
 * Reset panel lets you reset current level
 */
public class ResetPanel extends JPanel {

    private final GameStateManager gameStateManager;

    public ResetPanel(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        initialize();
    }

    public void initialize() {
        setBounds(700, 200, 150, 100);
        setFocusable(true);
        setLayout(null);

        JButton resetButton = new JButton();
        resetButton.setBounds(0, 0, 150, 100);
        resetButton.setText("reset level");
        resetButton.setForeground(new Color(227, 254, 247));
        resetButton.setBackground(new Color(19, 93, 102));
        resetButton.setFocusable(false);
        resetButton.setBorder(null);
        resetButton.addActionListener(e -> gameStateManager.setCurrentState(GameState.RESET_LEVEL));

        add(resetButton);
        setVisible(false);
    }

}
