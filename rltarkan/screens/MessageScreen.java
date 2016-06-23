package rltarkan.screens;

import java.util.List;
import asciiPanel.AsciiPanel;
import java.awt.event.KeyEvent;

public class MessageScreen implements Screen
{
    private Screen previousScreen;
    private int screenWidth;
    private int screenHeight;
    private int start;

    private List<String> messages;

    public MessageScreen(List<String> messages, Screen previous)
    {
        screenWidth = 80;
        screenHeight = 21;
        this.messages = messages;
        this.previousScreen = previous;
    }

    public void displayOutput(AsciiPanel terminal)
    {
        displayMessages(terminal);
    }

    public Screen respondToUserInput(KeyEvent key)
    {
        switch (key.getKeyCode())
        {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_K: scrollUp(); break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_J: scrollDown(); break;
            case KeyEvent.VK_M: return previousScreen;
        }
        return this;
    }

    private void displayMessages(AsciiPanel terminal)
    {
        for (int i = 0; i < Math.min(screenHeight, messages.size() - start); i++)
        {
            terminal.write(messages.get(i + start), 0, i);
        }
    }

    private void scrollDown()
    {
        start++;
        if (start >= messages.size() - 1) start = messages.size() - 1;
    }

    private void scrollUp()
    {
        start--;
        if (start < 0) start = 0;
    }

}
