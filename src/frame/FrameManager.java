package frame;

import game.GameState;
import game.componenets.*;
import game.componenets.GameTimer;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Frame manager controls which panels are visible
 */
public class FrameManager {

    private final MainMenu mainMenu;
    private final GameModeSelectionMenu gameModeSelectionMenu;
    private final LevelSelectionMenu levelSelectionMenu;
    private final GamePanel gamePanel;
    private final GameTimer gameTimer;
    private final ResetPanel resetPanel;
    private final ReturnToMenuPanel returnToMenuPanel;
    private final InformationPanel informationPanel;

    private final ArrayList<JPanel> panels = new ArrayList<>();

    private GameState lastGameState;

    public FrameManager(MainMenu mainMenu, GameModeSelectionMenu gameModeSelectionMenu, LevelSelectionMenu levelSelectionMenu, GamePanel gamePanel, GameTimer gameTimer, ResetPanel resetPanel, InformationPanel informationPanel, ReturnToMenuPanel returnToMenuPanel) {

        this.mainMenu = mainMenu;
        this.gameModeSelectionMenu = gameModeSelectionMenu;
        this.levelSelectionMenu = levelSelectionMenu;
        this.gamePanel = gamePanel;
        this.gameTimer = gameTimer;
        this.resetPanel = resetPanel;
        this.returnToMenuPanel = returnToMenuPanel;
        this.informationPanel = informationPanel;

        panels.add(this.mainMenu);
        panels.add(this.gameModeSelectionMenu);
        panels.add(this.levelSelectionMenu);
        panels.add(this.gamePanel);
        panels.add(this.gameTimer);
        panels.add(this.resetPanel);
        panels.add(this.returnToMenuPanel);
        panels.add(this.informationPanel);
    }

    /**
     * Method that updates the frames
     * @param newGameState game state that was passed for an update
     */
    public void update(GameState newGameState) {
        if (newGameState != lastGameState) {
            switch (newGameState) {
                case PLAYING, RESET_LEVEL, RUN_OUT_OF_TIME -> {

                    openAllExcept(new ArrayList<>(Arrays.asList(gameModeSelectionMenu, levelSelectionMenu, mainMenu)));
                    this.gamePanel.requestFocus();
                }
                case MAIN_MENU -> {
                    closeAllExcept(new ArrayList<>(Collections.singletonList(mainMenu)));
                    this.mainMenu.requestFocus();
                }
                case LEVEL_CHOICE -> {
                    closeAllExcept(new ArrayList<>(Collections.singletonList(levelSelectionMenu)));
                    this.levelSelectionMenu.requestFocus();
                }
                case GAME_MODE_CHOICE -> {
                    closeAllExcept(new ArrayList<>(Collections.singletonList(gameModeSelectionMenu)));

                    this.gameModeSelectionMenu.resetOption();
                    this.gameModeSelectionMenu.requestFocus();

                }
                default -> {
                    return;
                }
            }
            lastGameState = newGameState;
        }
    }

    /**
     * Closes all panels except the ones that are passed in
     * @param p ArrayList of panels that will stay visible
     */
    private void closeAllExcept(ArrayList<JPanel> p) {
        for (JPanel panel : panels) {
            panel.setVisible(p.contains(panel));
        }
    }

    /**
     * Opens all panels except the ones that are passed in
     * @param p ArrayList of panels that will stay invisible
     */
    private void openAllExcept(ArrayList<JPanel> p) {
        for (JPanel panel : panels) {
            panel.setVisible(!p.contains(panel));
        }
    }
}
