package game.componenets;

import game.Direction;

import java.awt.image.BufferedImage;

/**
 * Box
 */
public class Box {

    private int posX;
    private int posY;

    private final int starterPositionX;
    private final int starterPositionY;

    private final boolean defaultCorrectPosition;
    private boolean correctPosition;

    private final BufferedImage image;
    private final BufferedImage winnerImage;

    public Box(int posX, int posY, boolean defaultCorrectPosition, BufferedImage image, BufferedImage winnerImage) {
        this.posX = posX;
        this.posY = posY;
        this.defaultCorrectPosition = defaultCorrectPosition;
        this.correctPosition = defaultCorrectPosition;
        this.image = image;
        this.winnerImage = winnerImage;

        this.starterPositionX = posX;
        this.starterPositionY = posY;
    }

    /**
     * Move method changes the position of the box
     * @param d direction in which the box was moved in
     * @param speed how many pixels the box should move by
     */
    public void move(Direction d, int speed) {
        switch (d) {
            case UP -> posY -= speed;
            case DOWN -> posY += speed;
            case LEFT -> posX -= speed;
            case RIGHT -> posX += speed;
        }
    }

    /**
     * Methode gets the visuals of the box (Image)
     * @return BufferedImage based on box being on correct position
     */
    public BufferedImage getImage() {
        if (correctPosition) {
            return winnerImage;
        } else {
            return image;
        }
    }

    /**
     * Resets the position and correct state, by setting it to the default values
     */
    public void resetPosition() {
        this.posX = starterPositionX;
        this.posY = starterPositionY;
        setCorrectPosition(defaultCorrectPosition);
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public boolean isCorrectPosition() {
        return correctPosition;
    }

    public void setCorrectPosition(boolean correctPosition) {
        this.correctPosition = correctPosition;
    }
}
