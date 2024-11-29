package frame;

import game.GameMode;
import game.GameState;
import game.componenets.*;
import game.componenets.GameTimer;

import javax.swing.*;
import java.awt.*;

/**
 * Main panel, holds everything
 */
public class MainPanel extends JPanel implements Runnable {

    Thread gameThread = new Thread(this);
    GameStateManager gameStateManager;
    GameMode gameMode;

    GameModeSelectionMenu gameModeSelectionMenu;
    MainMenu mainMenu;
    GameTimer gameTimer;
    GamePanel gamePanel;
    ResetPanel resetPanel;
    ReturnToMenuPanel returnToMenuPanel;
    InformationPanel informationPanel;
    LevelSelectionMenu levelSelectionMenu;

    FrameManager frameManager;

    public MainPanel() {
        initialize();
    }

    public void initialize() {
        setBounds(0, 0, 900, 750);
        setBackground(new Color(0, 60, 67));
        setLayout(null);

        gameModeSelectionMenu = new GameModeSelectionMenu();
        levelSelectionMenu = new LevelSelectionMenu(10);
        gameStateManager = new GameStateManager();
        mainMenu = new MainMenu(gameStateManager);
        resetPanel = new ResetPanel(gameStateManager);
        returnToMenuPanel = new ReturnToMenuPanel(gameStateManager);
        informationPanel = new InformationPanel(gameStateManager);
        gameTimer = new GameTimer();
        gamePanel = new GamePanel(600, 500, gameTimer, gameStateManager, informationPanel);

        frameManager = new FrameManager(mainMenu, gameModeSelectionMenu, levelSelectionMenu, gamePanel, gameTimer, resetPanel, informationPanel, returnToMenuPanel);
        gameStateManager.setFrameManager(frameManager);

        add(gameModeSelectionMenu);
        add(mainMenu);
        add(resetPanel);
        add(gameTimer);
        add(informationPanel);
        add(returnToMenuPanel);
        add(levelSelectionMenu);

        add(gamePanel);
        setVisible(true);

        startGame();
    }

    /**
     * Starts the game after everything is initialized
     */
    public void startGame() {
        gameStateManager.setCurrentState(GameState.MAIN_MENU);
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Run method runs 60x per second, updates game based on game state
     */
    @Override
    public void run() {
        double runInterval = 1000000000 / 60;
        double nextInterval = System.nanoTime() + runInterval;

        GameState state = gameStateManager.getCurrentState();
        while (gameThread != null) {
            switch (state) {
                case GAME_MODE_CHOICE -> {
                    gameMode = gameModeSelectionMenu.getGameMode();
                    if (gameMode != null) {
                        gamePanel.setGameMode(gameMode);
                    }
                }
                case RESET_LEVEL -> {
                    gamePanel.resetLevel();
                }
                case PLAYING -> {
                    gamePanel.updateGame();
                }
                case LEVEL_CHOICE -> {
                    if (levelSelectionMenu.getSelectedLevel() > 0) {
                        gamePanel.setUpLevel(levelSelectionMenu.getSelectedLevel());
                        gameStateManager.setCurrentState(GameState.PLAYING);
                    }
                }
                case MAIN_MENU -> {
                    gameModeSelectionMenu.resetOption();
                    levelSelectionMenu.setSelectedLevel(0);
                }
            }

            try {
                double remainingTime = nextInterval - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);

                nextInterval += runInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            state = gameStateManager.getCurrentState();
        }
    }
}
