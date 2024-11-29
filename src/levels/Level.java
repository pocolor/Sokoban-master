package levels;

import game.Direction;
import game.componenets.Box;
import levels.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Level
 */
public class Level {

    private int levelNumber;
    private int timeAmount;

    private int playerSpawnX;
    private int playerSpawnY;

    private HashMap<String, Tile> tileMap = new HashMap<>();

    private Tile[][] tiles = new Tile[12][10];
    private ArrayList<Box> boxes = new ArrayList<>();

    public Level(String path) {
        createTiles();
        loadLevel(path);
    }

    /**
     * Creates HashMap of available tiles
     */
    public void createTiles() {

        try {
            tileMap.put("floor", new Tile(ImageIO.read(getClass().getResourceAsStream("/levels/tiles/floor.png")), TileType.FLOOR));
            tileMap.put("wall", new Tile(ImageIO.read(getClass().getResourceAsStream("/levels/tiles/wall.png")), TileType.WALL));
            tileMap.put("boxDestination", new Tile(ImageIO.read(getClass().getResourceAsStream("/levels/tiles/boxDestination.png")), TileType.BOX_DESTINATION));
            tileMap.put("void", new Tile(TileType.VOID));
        } catch (Exception e) {
            System.out.println("TILE IMAGE PATH DOES NOT EXIST");
        }
    }

    /**
     * Loads level based on file path into Array
     * @param path file path
     */
    public void loadLevel(String path) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            String line = "";

            while ((line = br.readLine()) != null) {
                if (line.charAt(0) == '#') {
                    String[] s = line.split("[=]");
                    if (s[0].equals("#levelNumber")) {
                        this.levelNumber = Integer.parseInt(s[1]);
                    } else {
                        this.timeAmount = Integer.parseInt(s[1]);
                    }
                } else {
                    String[] s = line.split("\\s");
                    for (int i = 0; i < s.length; i++) {
                        switch (s[i]) {
                            case "0" -> tiles[i][row] = tileMap.get("void");
                            case "1" -> tiles[i][row] = tileMap.get("floor");
                            case "2" -> tiles[i][row] = tileMap.get("boxDestination");
                            case "3" -> tiles[i][row] = tileMap.get("wall");
                            case "4" -> { //box
                                tiles[i][row] = tileMap.get("floor");
                                addBox(i, row, false);
                            }
                            case "5" -> { // player
                                tiles[i][row] = tileMap.get("floor");
                                playerSpawnX = i * 50;
                                playerSpawnY = row * 50;
                            }
                            case "6" -> {// box but on box destination
                                tiles[i][row] = tileMap.get("boxDestination");
                                addBox(i, row, true);
                            }
                        }
                    }
                    row++;
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds box into ArrayList
     * @param i - colum
     * @param row - row
     * @param defaultCorrectPosition if box is on correct position by default
     */
    private void addBox(int i, int row, boolean defaultCorrectPosition) {
        try {
            this.boxes.add(new Box(i * 50, row * 50, defaultCorrectPosition,
                    ImageIO.read(getClass().getResourceAsStream("/levels/tiles/grayBox.png")),
                    ImageIO.read(getClass().getResourceAsStream("/levels/tiles/yellowBox.png"))
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resets all the boxes position
     */
    public void resetBoxes() {
        for (Box box : boxes) {
            box.resetPosition();
        }
    }

    /**
     * Checks if box is on a
     * @param x position
     * @param y position
     * @return box or null
     */
    public Box checkBoxOnPosition(int x, int y) {
        for (Box box : boxes) {
            if (box.getPosX() == x && box.getPosY() == y) {
                return box;
            }
        }
        return null;
    }

    /**
     * Returns tile on position
     * @param x position
     * @param y position
     * @return the tile
     */
    public Tile getTileOnPosition(int x, int y) {
        return tiles[x / 50][y / 50];
    }

    /**
     * Checks if all boxes are on correct position
     * @return true or false
     */
    public boolean checkWin() {
        for (Box b : boxes) {
            if (!b.isCorrectPosition()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Draws the level
     * @param g2 Graphics2D
     */
    public void drawLevel(Graphics2D g2) {
        //floor
        g2.setColor(new Color(0, 60, 67));
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                if (tiles[i][j].getTileType() == TileType.VOID) {
                    g2.fillRect(i * 50, j * 50, 50, 50);
                } else {
                    g2.drawImage(tiles[i][j].getImage(), i * 50, j * 50, 50, 50, null);
                }
            }
        }
        //boxes
        for (Box box : boxes) {
            g2.drawImage(box.getImage(), box.getPosX(), box.getPosY(), 50, 50, null);
        }
    }

    /**
     * Gets box
     * @param d direction
     * @param x position
     * @param y position
     * @param checkSecondNextTile check the tile after the tile
     * @return Box or null
     */
    public Box getBox(Direction d, int x, int y, Boolean checkSecondNextTile) {
        int multiplayer = 1;
        if (checkSecondNextTile) {
            multiplayer = 2;
        }
        switch (d) {
            case UP -> {
                return checkBoxOnPosition(x, y - (50 * multiplayer));
            }
            case DOWN -> {
                return checkBoxOnPosition(x, y + (50 * multiplayer));
            }
            case LEFT -> {
                return checkBoxOnPosition(x - (50 * multiplayer), y);
            }
            case RIGHT -> {
                return checkBoxOnPosition(x + (50 * multiplayer), y);
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Gets tile
     * @param d direction
     * @param x position
     * @param y position
     * @param checkSecondNextTile to check the tile after the tile
     * @return
     */
    public Tile getNextTile(Direction d, int x, int y, Boolean checkSecondNextTile) {
        int multiplayer = 1;
        if (checkSecondNextTile) {
            multiplayer = 2;
        }
        switch (d) {
            case UP -> {
                return getTileOnPosition(x, y - (50 * multiplayer));
            }
            case DOWN -> {
                return getTileOnPosition(x, y + (50 * multiplayer));
            }
            case LEFT -> {
                return getTileOnPosition(x - (50 * multiplayer), y);
            }
            case RIGHT -> {
                return getTileOnPosition(x + (50 * multiplayer), y);
            }
            default -> {
                return null;
            }
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public int getPlayerSpawnX() {
        return playerSpawnX;
    }

    public int getPlayerSpawnY() {
        return playerSpawnY;
    }

    public int getTimeAmount() {
        return timeAmount;
    }
}
