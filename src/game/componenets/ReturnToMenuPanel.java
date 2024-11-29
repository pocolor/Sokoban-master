package game.componenets;

import game.GameState;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that allows player to return back to menu
 */
public class ReturnToMenuPanel extends JPanel {
    private JButton resetButton;
    private final GameStateManager gameStateManager;

    public ReturnToMenuPanel(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        initialize();
    }

    public void initialize(){
        setBounds(700, 350, 150, 100);
        setBackground(Color.red);
        setFocusable(true);
        setLayout(null);

        resetButton = new JButton();
        resetButton.setBounds(0, 0, 150, 100);
        resetButton.setText("return to menu");
        resetButton.setForeground(new Color(227, 254, 247));
        resetButton.setBackground(new Color(19, 93, 102));
        resetButton.setFocusable(false);
        resetButton.setBorder(null);
        resetButton.addActionListener(e -> gameStateManager.setCurrentState(GameState.MAIN_MENU));

        add(resetButton);
        setVisible(false);
    }
}
