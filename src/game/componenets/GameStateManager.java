package game.componenets;

import frame.FrameManager;
import game.GameState;

/**
 * Controls the game state that every component can access
 */
public class GameStateManager {
    private GameState currentState;

    private FrameManager frameManager;

    public void setFrameManager(FrameManager frameManager) {
        this.frameManager = frameManager;
    }

    public GameState getCurrentState() {
        return currentState;
    }

    /**
     * Sets the game state and updates the frame manager
     * @param currentState the game state
     */
    public void setCurrentState(GameState currentState) {
        this.currentState = currentState;
        this.frameManager.update(currentState);
    }
}
