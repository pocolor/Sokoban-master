package game.componenets;

import javax.swing.*;
import java.awt.*;

/**
 * Game timer, panel that holds remaining time
 */
public class GameTimer extends JPanel implements Runnable{

    private JLabel label;
    private JLabel timeLabel;
    private Thread timerThread;

    private int timerTime;
    private boolean runOutOfTime = false;
    private boolean infiniteTime = false;

    public GameTimer() {
        initialize();
    }

    public void initialize(){
        setBounds(700, 50, 150, 100);
        setFocusable(true);
        setLayout(null);

        label = new JLabel("REMAINING TIME:");
        label.setBounds(0,0,150,50);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(new Color(227, 254, 247));
        label.setBackground(new Color(19, 93, 102));
        label.setOpaque(true);

        timeLabel = new JLabel("0");
        timeLabel.setBounds(0,50,150,50);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setForeground(new Color(227, 254, 247));
        timeLabel.setBackground(new Color(19, 93, 102));
        timeLabel.setOpaque(true);

        add(label);
        add(timeLabel);
        setVisible(false);
    }

    /**
     * Checks if timer has run out of time
     * @return true or false
     */
    public boolean runOutOfTime(){
        if (infiniteTime){
            return false;
        }else {
            return runOutOfTime;
        }
    }

    /**
     * Starts a new timer
     * @param time time that the timer should have
     * @param infiniteTime if timer should be infinite
     */
    public void startNewTimer(int time, boolean infiniteTime) {
        reset();
        this.timerTime = time;
        if (!infiniteTime){
            this.timerThread.start();
        }else {
            timeLabel.setText("infinite");
        }
    }

    /**
     * Resets the timer
     */
    public void reset(){
        this.runOutOfTime = false;
        this.infiniteTime = false;
        if (this.timerThread != null){

            this.timerThread.interrupt();
        }

        this.timerThread = new Thread(this);
    }

    /**
     * Run method that makes the timer count down
     */
    @Override
    public void run() {
        while (timerTime > 0 && !infiniteTime){
            try {
                timerTime--;
                timeLabel.setText(String.valueOf(timerTime));
                Thread.sleep(1000);

            } catch (InterruptedException e) { // stops through interruption because "stop" variable can change faster than Thread.sleep finishes (player exiting and loading level to fast)
                //time stopped
                return;
            }
        }
        runOutOfTime = true;
    }
}
