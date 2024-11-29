package frame;

import javax.swing.*;
import java.awt.*;

/**
 * Main frame
 */
public class MainFrame extends JFrame {
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(900, 750));
        setResizable(false);

        setTitle("Sokoban");

        setLayout(null);
        MainPanel mainPanel = new MainPanel();
        add(mainPanel);

        setVisible(true);
    }

}
