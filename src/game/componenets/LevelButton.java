package game.componenets;

import javax.swing.*;
import java.awt.*;

/**
 * Level button is a clickable button when player chooses his level
 */
public class LevelButton extends JButton {

    public LevelButton(LevelSelectionMenu menu , int levelNumber) {

        setForeground(new Color(119, 176, 170));
        setBackground(new Color(19, 93, 102));
        setFocusable(false);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(new Color(0, 60, 67),2));
        setText(String.valueOf(levelNumber));
        setFont(new Font("Dialog", Font.PLAIN, 25));
        addActionListener(e -> menu.setSelectedLevel(levelNumber)); // this is why I need separated class
    }
}
