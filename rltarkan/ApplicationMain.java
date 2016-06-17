package rltarkan;

import javax.swing.JFrame;
import asciiPanel.AsciiPanel;

public class ApplicationMain extends JFrame {
    private static final long serialVersionUID = 1060623638149583738L;

    private AsciiPanel terminal;

    public ApplicationMain(){
        super();
        terminal = new AsciiPanel();
        terminal.write("The first parameter is how many characters", 10, 0);
        terminal.write("in the horizontal direction", 10, 1);
        terminal.write("And the second is how many characters in the vertical direction", 0, 10);
        add(terminal);
        pack();
    }

    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
