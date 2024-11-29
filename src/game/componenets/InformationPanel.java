package game.componenets;

import javax.swing.*;
import java.awt.*;

/**
 * Information panel that holds information about the current game
 */
public class InformationPanel extends JPanel {
    private final GameStateManager gameStateManager;
    private JLabel informationLabel;

    private int levelNumber;

    public InformationPanel(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        initialize();
    }

    public void initialize(){
        setBounds(50, 600, 300, 50);
        setFocusable(true);
        setLayout(null);

        informationLabel = new JLabel();
        informationLabel.setBounds(0, 0, 300, 50);
        informationLabel.setHorizontalAlignment(SwingConstants.CENTER);
        informationLabel.setText("INFO:");
        informationLabel.setForeground(new Color(227, 254, 247));
        informationLabel.setBackground(new Color(19, 93, 102));
        informationLabel.setFocusable(false);
        informationLabel.setOpaque(true);
        informationLabel.setBorder(null);

        add(informationLabel);
        setVisible(false);
    }

    /**
     * updates the label based on game state
     */
    public void update(){
        switch (gameStateManager.getCurrentState()){
            case PLAYING -> {
                informationLabel.setText("Playing level number: " + levelNumber);
                informationLabel.setForeground(new Color(227, 254, 247));
            }
            case WINNER -> {
                informationLabel.setText("You won");
                informationLabel.setForeground(new Color(123, 239, 15));
            }
            case RUN_OUT_OF_TIME -> {
                informationLabel.setText("You have run out of time");
                informationLabel.setForeground(new Color(239, 15, 15));
            }
        }

    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

}
