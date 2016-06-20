package rltarkan.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import java.util.List;
import rltarkan.Creature;
import rltarkan.CreatureFactory;
import rltarkan.World;
import rltarkan.WorldBuilder;

public class PlayScreen implements Screen {

    private World world; // World file (randomly generated in createWorld)
    private Creature player;
    private int screenWidth;
    private int screenHeight;

    public PlayScreen()
    {
        screenWidth = 80;
        screenHeight = 21;
        createWorld();

        CreatureFactory creatureFactory = new CreatureFactory(world);
        createCreatures(creatureFactory);
    }

    private void createWorld()
    {
        //specifies how big of a world we want
        //makes the caves
        //gives us the world object
        world = new WorldBuilder(90, 31).makeCaves().build();
    }

    private void createCreatures(CreatureFactory creatureFactory)
    {
        for (int i = 0; i < 18; i++)
        {
            creatureFactory.newFungus();
        }
        player = creatureFactory.newPlayer();
    }

    // Displays tiles
    // @param left is the tile
    private void displayTiles(AsciiPanel terminal, int left, int top)
    {
        for (int x = 0; x < screenWidth; x++)
        {
            for (int y = 0; y < screenHeight; y++)
            {
                int wx = x + left;
                int wy = y + top;

                terminal.write(world.glyph(wx, wy), x, y, world.color(wx, wy));
            }
        }
        for (Creature c : world.creatures())
        {
            if (c.x >= left && c.x < left + screenWidth && c.y >= top && c.y < top + screenHeight)
            {
                terminal.write(c.glyph(), c.x - left, c.y - top, c.color());
            }
        }
    }

    public void displayOutput(AsciiPanel terminal)
    {
        int left = getScrollX();
        int top = getScrollY();
        displayTiles(terminal, left, top);
        terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 22);
    }

    public Screen respondToUserInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE: return new LoseScreen();
            case KeyEvent.VK_ENTER: return new WinScreen();
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H: player.moveBy(-1, 0); break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L: player.moveBy( 1, 0); break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K: player.moveBy( 0,-1); break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J: player.moveBy( 0, 1); break;
            case KeyEvent.VK_Y: player.moveBy(-1,-1); break;
            case KeyEvent.VK_U: player.moveBy( 1,-1); break;
            case KeyEvent.VK_B: player.moveBy(-1, 1); break;
            case KeyEvent.VK_N: player.moveBy( 1, 1); break;
        }
        world.update();
        return this;
    }

    public int getScrollX()
    {
        return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY()
    {
        return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
    }
}
