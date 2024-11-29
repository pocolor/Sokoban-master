package game.componenets;

import game.GameMode;

import javax.swing.*;
import java.awt.*;

/**
 * Panel that lets you choose between two game modes
 */
public class GameModeSelectionMenu extends JPanel {

    private final JButton option1Button = new JButton();
    private final JButton option2Button = new JButton();

    private final JLabel headLabel = new JLabel();
    private final JLabel option1Label = new JLabel();
    private final JLabel option2Label = new JLabel();

    private GameMode option;

    public GameModeSelectionMenu() {
        initialize();
    }

    public void initialize(){
        setBounds(0,0,900,750);
        setBackground(new Color(0, 60, 67));
        setLayout(null);

        headLabel.setBounds(100,100,700,150);
        headLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headLabel.setText("SELECT YOUR GAME MODE");
        headLabel.setForeground(new Color(119, 176, 170));
        headLabel.setBackground(new Color(19, 93, 102));
        headLabel.setOpaque(true);
        headLabel.setFont(new Font("Ariel", Font.PLAIN, 50));

        option1Button.setBounds(100,350,200,100);
        option1Button.setHorizontalAlignment(SwingConstants.CENTER);
        option1Button.setText("NORMAL MODE");
        option1Button.setForeground(new Color(119, 176, 170));
        option1Button.setBackground(new Color(19, 93, 102));
        option1Button.setFocusable(false);
        option1Button.setBorder(null);
        option1Button.setFont(new Font("Ariel", Font.PLAIN, 25));
        option1Button.addActionListener(e -> option = GameMode.NORMAL);

        option2Button.setBounds(600,350,200,100);
        option2Button.setHorizontalAlignment(SwingConstants.CENTER);
        option2Button.setText("FREE MODE");
        option2Button.setForeground(new Color(119, 176, 170));
        option2Button.setBackground(new Color(19, 93, 102));
        option2Button.setFocusable(false);
        option2Button.setBorder(null);
        option2Button.setFont(new Font("Ariel", Font.PLAIN, 25));
        option2Button.addActionListener(e -> option = GameMode.FREE);

        option1Label.setBounds(100,450,200,100);
        option1Label.setText("<HTML><body style='text-align: center'>go through each level one by one, <BR>get as far as possible or run out of time</HTML>");
        option1Label.setForeground(new Color(227, 254, 247));

        option2Label.setBounds(600,450,200,100);
        option2Label.setText("<HTML><body style='text-align: center'>select one level you want to play, <BR>you have as much time as you want</HTML>");
        option2Label.setForeground(new Color(227, 254, 247));

        add(option1Button);
        add(option2Button);
        add(option1Label);
        add(option2Label);
        add(headLabel);

        setVisible(false);
    }

    public GameMode getGameMode(){
        return option;
    }

    /**
     * Resets the option so a new one can be selected
     */
    public void resetOption(){
        this.option = null;
    }
}
