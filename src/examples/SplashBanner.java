package examples;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SplashBanner extends JWindow {

    private int percent = 0;
    private Timer time;
    private JProgressBar progessBar;
    private JLabel splashImage;
    private JLabel lb;
    private SenderAgent senderAgent;
    private MainBoard mainBoard;

    public SplashBanner(SenderAgent senderAgent) {
        this.senderAgent = senderAgent;
    }
    
    public MainBoard getMainBoard() {
        return mainBoard;
    }
    
    public void showSplash() {
        JPanel content = (JPanel) getContentPane();
        int width = 380;
        int height = 200;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        setBounds(x, y, width, height);
        splashImage = new JLabel();
        lb = new JLabel();
        lb.setIcon(new ImageIcon(getClass().getResource("/images/MI6.gif")));
        lb.setHorizontalAlignment(JLabel.CENTER);
//        progessBar = new javax.swing.JProgressBar();
//        progessBar.setForeground(Color.YELLOW);
//        progessBar.setVisible(true);
        ActionListener action = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                percent += (Math.random() * 20);
                if (percent > 50) {
                    boolean flag = true;
                }
                if (percent >= 100) {
                    time.stop();
                    dispose();
                    mainBoard = new MainBoard(senderAgent);
                    mainBoard.setVisible(true);
                    SenderAgent.ReceiveMessage rm = senderAgent.new ReceiveMessage();
                    senderAgent.addBehaviour(rm);
                    SenderAgent.GetAgentListBehaviour alb = senderAgent.new GetAgentListBehaviour(senderAgent, 5000);
                    senderAgent.addBehaviour(alb);
                }
//                progessBar.setValue(percent);
            }
        };
        // call action every 200 ms
        time = new Timer(300, action);
//        progessBar.setValue(0);
        // timer starts
        time.start();
        content.add(splashImage, BorderLayout.CENTER);
        // create a panel to contain the label and the progess bar
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(lb, BorderLayout.CENTER);
//        panel.add(progessBar, BorderLayout.PAGE_END);
        content.add(panel, BorderLayout.CENTER);
        // Display it
        setVisible(true);
    }
//
//    public static void main(String[] args) {
//        SplashBanner aaa = new SplashBanner(new SenderAgent());
//        aaa.showSplash();
//    }
}
