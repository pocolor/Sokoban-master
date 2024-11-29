package game.componenets;

import game.Direction;
import game.GameState;
import game.movement.KeyHandler;
import game.movement.Movement;
import game.player.Player;
import levels.Level;
import levels.LevelManager;
import levels.TileType;
import levels.tiles.Tile;
import game.GameMode;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * Game panel is a "main" panel which shows the game
 */
public class GamePanel extends JPanel {

    KeyHandler keyHandler = new KeyHandler();
    Stack<Movement> stack = new Stack<>();
    private Player player;

    private final int width;
    private final int height;

    private final LevelManager levelManager = new LevelManager();
    private Level level;
    private GameMode gameMode;
    private final GameStateManager gameStateManager;
    private final InformationPanel informationPanel;

    private final GameTimer gameTimer;

    public GamePanel(int width, int height, GameTimer gameTimer, GameStateManager gameStateManager, InformationPanel informationPanel) {

        this.width = width;
        this.height = height;
        this.gameStateManager = gameStateManager;

        this.gameTimer = gameTimer;
        this.informationPanel = informationPanel;

        initialize();
    }

    private void initialize() {
        setBounds(50, 50, width, height);
        setBackground(Color.GRAY);
        setFocusable(true);

        addKeyListener(keyHandler);
    }

    /**
     * Sets up a new level
     * @param levelNumber number of level to set up
     */
    public void setUpLevel(int levelNumber) {
        levelManager.setCurrentLevel(levelNumber);

        this.level = levelManager.getCurrentLevel();
        this.player = new Player(level.getPlayerSpawnX(), level.getPlayerSpawnY());
        resetLevel();
        gameTimer.startNewTimer(level.getTimeAmount(), gameMode == GameMode.FREE);
        informationPanel.setLevelNumber(level.getLevelNumber());
        gameStateManager.setCurrentState(GameState.PLAYING);
        informationPanel.update();
    }

    /**
     * Resets the current level
     */
    public void resetLevel() {
        this.level.resetBoxes();
        this.player.resetPlayer();
        this.stack.clear();
        if (!gameTimer.runOutOfTime() || gameStateManager.getCurrentState() == GameState.WINNER) {
            gameStateManager.setCurrentState(GameState.PLAYING);
        }
        informationPanel.update();
    }


    private Direction direction = Direction.NONE;
    private Direction lastDirection = Direction.NONE;

    private int boxMoved = 0; //if box was moved more tiles in one direction
    private final int speed = 3; // movement speed
    private Box box; //current box

    /**
     * "Main" updating method for the game
     */
    public void updateGame() {

        int playerX = player.getPosX();
        int playerY = player.getPosY();

        //TIMER
        if (gameTimer.runOutOfTime()) { // if player runs out of time
            gameStateManager.setCurrentState(GameState.RUN_OUT_OF_TIME);
            informationPanel.update();
            return;
        }

        //WINNING
        if ((direction == Direction.NONE && level.checkWin()) || gameStateManager.getCurrentState() == GameState.WINNER) { // if player wins the game
            if (gameMode == GameMode.NORMAL) {
                int next = levelManager.nextLevel();
                if (next != 0) { // if there still are levels
                    setUpLevel(levelManager.nextLevel());
                } else {
                    gameStateManager.setCurrentState(GameState.WINNER);
                    gameTimer.reset();
                }
            } else {
                gameStateManager.setCurrentState(GameState.WINNER);
            }
            informationPanel.update();
            return;
        }

        //REVERT MOVEMENT
        if (keyHandler.revertMovement) { // if player pressed R to revert his movement
            if (!stack.isEmpty()) {

                Movement m = stack.pop();
                player.setPosX(m.getPlayerX());
                player.setPosY(m.getPlayerY());

                m.getBox().setPosX(m.getBoxX());
                m.getBox().setPosY(m.getBoxY());

                m.getBox().setCorrectPosition(level.getTileOnPosition(m.getBoxX(), m.getBoxY()).getTileType() == TileType.BOX_DESTINATION);

                keyHandler.revertMovement = false;
                boxMoved = 0; // fixes if player spams revert when moving box
                return;
            } else {
                keyHandler.revertMovement = false;
            }
        }

        //PLAYER AND BOX MOVEMENT
        if (playerX % 50 != 0 || playerY % 50 != 0) { // if player is still moving

            int remaining = calculateRemaining(lastDirection, playerX, playerY);
            player.move(lastDirection, Math.min(speed, remaining));

            if (box != null) { // if box is being pushed
                box.move(lastDirection, Math.min(speed, remaining));
            }
        } else {
            direction = keyHandler.direction;

            if (direction != Direction.NONE) { // if player is moving

                Tile nextTile = level.getNextTile(direction, playerX, playerY, false);

                if (nextTile.getTileType() != TileType.WALL) { // if next tile is not a wall

                    box = level.getBox(direction, playerX, playerY, false);

                    if (box != null) { // if the next tile has a box
                        if (lastDirection != direction) { //fixes adding movement to the stack
                            boxMoved = 0;
                        }
                        Tile tileBehindBox = level.getNextTile(direction, playerX, playerY, true);

                        if (tileBehindBox.getTileType() != TileType.WALL &&
                                level.getBox(direction, playerX, playerY, true) == null) { // if there is no wall or box behind the box
                            if (boxMoved == 0) {
                                stack.add(new Movement(box, box.getPosX(), box.getPosY(), direction));
                            }
                            box.move(direction, speed);
                            player.move(direction, speed);

                            boxMoved++;
                            box.setCorrectPosition(tileBehindBox.getTileType() == TileType.BOX_DESTINATION);
                        }
                    } else {
                        player.move(direction, speed);
                        boxMoved = 0;// also for fix
                    }
                    lastDirection = direction;
                }
            } else {
                boxMoved = 0;
            }

        }
        repaint();
    }

    /**
     * Calculates remaining pixels to in order to stay in the grid
     * @param d direction in which the player is moving
     * @param playerX player x position
     * @param playerY player y position
     * @return remaining pixels
     */
    private int calculateRemaining(Direction d, int playerX, int playerY) {
        switch (d) {
            case UP -> {
                return playerY % 50;
            }
            case DOWN -> {
                return 50 - (playerY % 50);
            }
            case LEFT -> {
                return playerX % 50;
            }
            case RIGHT -> {
                return 50 - (playerX % 50);
            }
            default -> {
                return 0;
            }
        }
    }

    /**
     * Sets the game mode, if game mode is "normal" the game starts at level 1, if game mode is "free" let player choose his level
     * @param gameMode chosen game mode
     */
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        if (gameMode == GameMode.FREE) {
            gameStateManager.setCurrentState(GameState.LEVEL_CHOICE);
        } else {
            setUpLevel(1);
        }
    }

    /**
     * Paints the level and then the player
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        level.drawLevel(g2);
        player.drawPlayer(g2, direction, lastDirection, box != null);
    }
}
