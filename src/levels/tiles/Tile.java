package levels.tiles;

import levels.TileType;
import java.awt.image.BufferedImage;

/**
 * Tile on the map, holds BufferedImage and Tile type
 */
public class Tile {

    private final BufferedImage image;
    private final TileType tileType;

    public Tile(BufferedImage image, TileType tileType) {
        this.image = image;
        this.tileType = tileType;
    }

    public Tile(TileType tileType) {
        this.tileType = tileType;
        this.image = null;
    }

    public BufferedImage getImage() {
        return image;
    }

    public TileType getTileType() {
        return tileType;
    }

}
