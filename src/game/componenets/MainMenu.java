package game.componenets;

import game.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Main menu
 */
public class MainMenu extends JPanel {

    private final GameStateManager gameStateManager;
    public MainMenu(GameStateManager gameStateManager) {
        this.gameStateManager = gameStateManager;
        initialize();
    }

    private void initialize(){
        setBounds(0,0,900,750);
        setBackground(new Color(0, 60, 67));
        setLayout(null);

        JButton button = new JButton();
        button.setBounds(100,450,700,100);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setText("CLICK HERE TO START THE GAME");
        button.setForeground(new Color(119, 176, 170));
        button.setBackground(new Color(19, 93, 102));
        button.setFocusable(false);
        button.setBorder(null);
        button.setFont(new Font("Ariel", Font.PLAIN, 25));
        button.addActionListener(e -> gameStateManager.setCurrentState(GameState.GAME_MODE_CHOICE));

        JLabel logo = new JLabel();
        logo.setBounds(100,150,700,200);
        logo.setBackground(new Color(0, 60, 67));
        logo.setOpaque(true);

        try {
            BufferedImage img = ImageIO.read(getClass().getResourceAsStream("/frame/logo.png"));
            Image img2 = img.getScaledInstance(logo.getWidth(), logo.getHeight(),Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(img2));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        add(logo);
        add(button);
        setVisible(false);
    }
}
