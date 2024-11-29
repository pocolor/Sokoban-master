package game.movement;

import game.componenets.Box;
import game.Direction;

/**
 * Movement that the player made when he moved the box
 */
public class Movement {
    private final int boxX;
    private final int boxY;

    private int playerX;
    private int playerY;

    private final Box box;

    public Movement(Box box, int boxX, int boxY, Direction direction) {
        this.box = box;
        this.boxX = boxX;
        this.boxY = boxY;

        setPlayerPosition(direction);
    }

    /**
     * calculates the player position
     * @param direction in which the box was moved
     */
    private void setPlayerPosition(Direction direction){
        switch (direction){
            case UP -> {playerX = boxX; playerY = boxY+50;}
            case DOWN -> {playerX = boxX; playerY = boxY-50;}
            case LEFT ->  {playerX = boxX+50; playerY = boxY;}
            case RIGHT -> {playerX = boxX-50; playerY = boxY;}
        }
    }

    public int getBoxX() {
        return boxX;
    }

    public int getBoxY() {
        return boxY;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public Box getBox() {
        return box;
    }

}
