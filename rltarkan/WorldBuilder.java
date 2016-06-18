package rltarkan;

public class WorldBuilder {
    public static final int SMOOTH_ITERATIONS = 8;
    private int width;
    private int height;
    private Tile[][] tiles;

    public WorldBuilder(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
    }

    public World build()
    {
        return new World(tiles);
    }

    public WorldBuilder makeCaves()
    {
        return randomizeTiles().smooth(SMOOTH_ITERATIONS);
    }

    private WorldBuilder randomizeTiles()
    {
        for (int x = 0; x < width; x++)
        {
            for (int y = 0; y < height; y++)
            {
                tiles[x][y] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
            }
        }
        return this;
    }

    private WorldBuilder smooth(int times)
    {
        Tile[][] tiles2 = new Tile[width][height];
        for (int time = 0; time < times; time++)
        {
            // Loops through every tile in the map
            for (int x = 0; x < width; x++)
            {
                for (int y = 0; y < height; y++)
                {
                    int floors = 0;
                    int rocks = 0;
                    // Loops through every tile around the specified tile (including itself)
                    for (int ox = -1; ox < 2; ox++)
                    {
                        for (int oy = -1; oy < 2; oy++)
                        {
                            // Ignores looking at tiles that are out of bounds
                            if (x + ox < 0 || x + ox >= width || y + oy < 0
                            || y + oy >= height)
                            continue; // This statement jumps to the next part of the for loop (or any loop)

                            if (tiles[x + ox][y + oy] == Tile.FLOOR)
                            floors++;

                            else
                            rocks++;
                        }
                    }
                    // If in the 3x3 square around the tile there are more or the same number of floors as walls, make it a floor. Else, make the tile a wall
                    tiles2[x][y] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
                }
            }
            tiles = tiles2; // Replaces tiles with tiles2 to continue iterating
        }
        return this;
    }

}
