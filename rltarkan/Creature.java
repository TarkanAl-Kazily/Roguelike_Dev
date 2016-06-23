/*
This is a base class for monsters, players, and other creatures in the game
*/
package rltarkan;

import java.awt.Color;

public class Creature
{
    public static final int NOTIFICATION_RADIUS = 9;
    // We want the creature to be able to reference the world it is in
    private World world;

    // These are public because they will be frequently accessed and changed
    public int x;
    public int y;

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    private int maxHp;
    public int maxHp() { return maxHp; }

    private int hp;
    public int hp() { return hp; }

    private int attackValue;
    public int attackValue() { return attackValue; }

    private int defenseValue;
    public int defenseValue() { return defenseValue; }

    public Creature(World world, char glyph, Color color)
    {
        this(world, glyph, color, 1, 0, 0);
    }

    public Creature(World world, char glyph, Color color, int maxHp, int attack, int defense)
    {
        this.world = world;
        this.glyph = glyph;
        this.color = color;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackValue = attack;
        this.defenseValue = defense;
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
        Creature other = world.creature(x + mx, y + my);

        if (other == null)
        ai.onEnter(x + mx, y + my, world.tile(x + mx, y + my));
        else
        {
            attack(other);
            // If the creature is still alive, it hits back
            // TODO remove and add attack functionality to other creature's update methods
            if (world.creatures().contains(other))
            {
                other.attack(this);
            }
        }
    }

    public void attack(Creature other)
    {
        int amount = Math.max(0, attackValue() - other.defenseValue());

        amount = (int) (Math.random() * amount) + 1;
        doAction("attack the '%s' for %d damage", other.glyph, amount);

        other.modifyHp(-amount);

    }

    public void modifyHp(int amount)
    {
        hp += amount;

        if (dead())
        {
            doAction("die");
            world.remove(this);
        }
    }

    public boolean dead()
    {
        return hp < 1;
    }

    public void update()
    {
        ai.onUpdate();
    }

    public boolean canEnter(int wx, int wy)
    {
        return world.tile(wx, wy).isGround() && world.creature(wx, wy) == null;
    }

    public void notify(String message, Object ... params)
    {
        ai.onNotify(String.format(message, params));
    }

    public void doAction(String message, Object ... params)
    {
        int r = NOTIFICATION_RADIUS;
        for (int ox = -r; ox < r+1; ox++)
        {
            for (int oy = -r; oy < r+1; oy++)
            {
                if (ox*ox + oy*oy > r*r)
                continue;

                Creature other = world.creature(x + ox, y + oy);

                if (other == null)
                continue;

                if (other == this)
                    other.notify("You " + message + ".", params);
                else
                    other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);
            }
        }
    }

    public String makeSecondPerson(String text)
    {
        String[] words = text.split(" ");
        words[0] = words[0] + "s";

        StringBuilder builder = new StringBuilder();
        for (String word : words){
            builder.append(" ");
            builder.append(word);
        }

        return builder.toString().trim();
    }
}
