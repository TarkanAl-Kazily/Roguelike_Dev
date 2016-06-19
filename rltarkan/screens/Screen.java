/*
The displayOutput method takes an AsciiPanel to display itself on and the respondToUserInput takes the KeyEvent and can return the new screen. This way pressing a key can result in looking at a different screen.
*/

package rltarkan.screens;

import java.awt.event.KeyEvent;
import asciiPanel.AsciiPanel;

public interface Screen
{

    public void displayOutput(AsciiPanel terminal);

    public Screen respondToUserInput(KeyEvent key);

}
