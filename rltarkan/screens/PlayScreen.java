package rltarkan.screens;

import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;
import rltarkan.Creature;
import rltarkan.World;
import rltarkan.WorldBuilder;
import rltarkan.CreatureFactory;

public class PlayScreen implements Screen {

    private World world; // World file (randomly generated in createWorld)
    /* Code without player
    private int centerX; // The X location we are looking at (player location)
    private int centerY; // The Y location we are looking at (player location)
    */
    private Creature player;
    private int screenWidth;
    private int screenHeight;

    public PlayScreen()
    {
        screenWidth = 80;
        screenHeight = 21;
        createWorld();
        CreatureFactory creatureFactory = new CreatureFactory(world);
        player = creatureFactory.newPlayer();
    }

    private void createWorld()
    {
        //specifies how big of a world we want
        //makes the caves
        //gives us the world object
        world = new WorldBuilder(90, 31).makeCaves().build();
    }

    /* Code without player
    public void displayOutput(AsciiPanel terminal)
    {
        int left = getScrollX();
        int top = getScrollY();
        displayTiles(terminal, left, top);
        terminal.write('X', centerX - left, centerY - top);
        terminal.writeCenter("-- press [escape] to lose or [enter] to win --", 22);
    }

    public Screen respondToUserInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE: return new LoseScreen();
            case KeyEvent.VK_ENTER: return new WinScreen();
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_H: scrollBy(-1, 0); break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_L: scrollBy( 1, 0); break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K: scrollBy( 0,-1); break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J: scrollBy( 0, 1); break;
            case KeyEvent.VK_Y: scrollBy(-1,-1); break;
            case KeyEvent.VK_U: scrollBy( 1,-1); break;
            case KeyEvent.VK_B: scrollBy(-1, 1); break;
            case KeyEvent.VK_N: scrollBy( 1, 1); break;
        }

        return this;
    }

    public int getScrollX()
    {
        return Math.max(0, Math.min(centerX - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY()
    {
        return Math.max(0, Math.min(centerY - screenHeight / 2, world.height() - screenHeight));
    }
    */

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
    }

    /* Code without player
    private void scrollBy(int mx, int my){
        player.x = Math.max(0, Math.min(centerX + mx, world.width() - 1));
        centerY = Math.max(0, Math.min(centerY + my, world.height() - 1));
    }
    */

    public void displayOutput(AsciiPanel terminal)
    {
        int left = getScrollX();
        int top = getScrollY();
        displayTiles(terminal, left, top);
        terminal.write(player.glyph(), player.x - left, player.y - top, player.color());
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
