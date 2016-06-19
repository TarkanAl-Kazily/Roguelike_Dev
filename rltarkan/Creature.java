/*
This is a base class for monsters, players, and other creatures in the game
*/

package rltarkan;

import java.awt.Color;

public class Creature
{
    // We want the creature to be able to reference the world it is in
    private World world;

    // These are public because they will be frequently accessed and changed
    public int x;
    public int y;

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    public Creature(World world, char glyph, Color color)
    {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
    }

    // The CreatureAi gives each creature its set of behavoirs and traits
    private CreatureAi ai;
    public void setCreatureAi(CreatureAi ai) { this.ai = ai; }

    // Rather than make the world fully navigable on each generation, we can just enable our creatures to dig through walls.
    public void dig(int wx, int wy)
    {
        world.dig(wx, wy);
    }

    // Movement of the creature is dictated by the creature's ai
    public void moveBy(int mx, int my)
    {
        ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
    }
}
