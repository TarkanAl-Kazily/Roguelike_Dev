package rltarkan;

import java.awt.Color;
import asciiPanel.AsciiPanel;

public enum Tile
{
    FLOOR((char)250, AsciiPanel.yellow),
    WALL((char)177, AsciiPanel.yellow),
    BOUNDS('x', AsciiPanel.brightBlack); // The kind of tile that is out of bounds. A null tile so to speak.

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }

    Tile(char glyph, Color color)
    {
        this.glyph = glyph;
        this.color = color;
    }
}
