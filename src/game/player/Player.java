package game.player;

import game.Direction;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Player
 */
public class Player {

    private int posX;
    private int posY;

    private final int starterPositionX;
    private final int starterPositionY;

    private final BufferedImage stand;
    private final BufferedImage walk1;
    private final BufferedImage walk2;
    private final BufferedImage push1;
    private final BufferedImage push2;

    // for player movement visuals
    private int delta;
    private int state;

    public Player(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;

        this.starterPositionX = posX;
        this.starterPositionY = posY;

        try {
            this.stand = ImageIO.read(getClass().getResourceAsStream("/game/player/player_stand.png"));
            this.walk1 = ImageIO.read(getClass().getResourceAsStream("/game/player/player_walk1.png"));
            this.walk2 = ImageIO.read(getClass().getResourceAsStream("/game/player/player_walk2.png"));
            this.push1 = ImageIO.read(getClass().getResourceAsStream("/game/player/player_push1.png"));
            this.push2 = ImageIO.read(getClass().getResourceAsStream("/game/player/player_push2.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Moves player
     * @param d direction in which player moved
     * @param speed determines by how many pixels player should move
     */
    public void move(Direction d, int speed){
        switch (d){
            case UP -> updateY(-speed);
            case DOWN ->  updateY(speed);
            case LEFT -> updateX(-speed);
            case RIGHT -> updateX(speed);
        }
    }

    /**
     * Resets player position by setting it to the default position
     */
    public void resetPlayer(){
        this.posX = starterPositionX;
        this.posY = starterPositionY;
    }

    /**
     * Draws the player
     * @param g2 Graphics2D
     * @param d direction player is heading
     * @param lastDirection last direction
     * @param push if player is pushing a box
     */
    public void drawPlayer(Graphics2D g2, Direction d, Direction lastDirection, boolean push){
        if (d != Direction.NONE){
            rotate(g2, d);
            if (push){
                if (state == 0){
                    g2.drawImage(push1,posX,posY,null);
                }else {
                    g2.drawImage(push2,posX,posY,null);
                }
            }else {
                if (state == 0){
                    g2.drawImage(walk1,posX,posY,null);
                }else {
                    g2.drawImage(walk2,posX,posY,null);
                }
            }

        }else {
            rotate(g2, lastDirection);
            g2.drawImage(stand,posX,posY,null);
        }

        delta++;
        if (delta > 10) {
            if (state == 0) {
                state = 1;
            } else {
                state = 0;
            }
            delta = 0;
        }
    }

    /**
     * Rotates the Graphics2D based on direction, so it can draw the image rotated
     * @param g2 Graphics2D
     * @param d direction
     */
    private void rotate(Graphics2D g2, Direction d){
        switch (d){
            case UP ->  g2.rotate(Math.toRadians(0),posX+25,posY+25);
            case DOWN -> g2.rotate(Math.toRadians(180),posX+25,posY+25);
            case LEFT -> g2.rotate(Math.toRadians(270),posX+25,posY+25);
            case RIGHT -> g2.rotate(Math.toRadians(90),posX+25,posY+25);
        }
    }

    /**
     * Updates the X position
     * @param value that is added to the X position
     */
    public void updateX(int value){
        this.posX += value;
    }

    /**
     * Updates the Y position
     * @param value that is added to the Y position
     */
    public void updateY(int value){
        this.posY += value;
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

}
