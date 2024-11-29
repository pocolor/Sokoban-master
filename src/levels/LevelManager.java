package levels;

import java.util.ArrayList;

/**
 * Contains all the levels, holds the current one
 */
public class LevelManager {
    private final ArrayList<Level> levels = new ArrayList<>();
    private Level currentLevel;

    public LevelManager() {
        initializeLevels();
    }

    private void initializeLevels(){
        levels.add(new Level("/levels/levelFiles/level_one.txt"));
        levels.add(new Level("/levels/levelFiles/level_two.txt"));
        levels.add(new Level("/levels/levelFiles/level_three.txt"));
        levels.add(new Level("/levels/levelFiles/level_four.txt"));
        levels.add(new Level("/levels/levelFiles/level_five.txt"));
        levels.add(new Level("/levels/levelFiles/level_six.txt"));
        levels.add(new Level("/levels/levelFiles/level_seven.txt"));
        levels.add(new Level("/levels/levelFiles/level_eight.txt"));
        levels.add(new Level("/levels/levelFiles/level_nine.txt"));
        levels.add(new Level("/levels/levelFiles/level_ten.txt"));
    }

    /**
     * Sets the next level as the current one
     * @return number of the level or 0 if there are no more levels
     */
    public int nextLevel(){
        int next = this.currentLevel.getLevelNumber()+1;
        if (next <= levels.size()){
            return next;
        }else {
            return 0; //no more levels available
        }
    }

    /**
     * Sets current level
     * @param levelNumber number of the level
     */
    public void setCurrentLevel(int levelNumber){
        for (Level l: levels) {
            if (l.getLevelNumber() == levelNumber){
                this.currentLevel = l;
                break;
            }
        }
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}
