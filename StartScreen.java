package LearningJava.MusicPlayer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartScreen extends JFrame implements ActionListener {
    JButton button;
    JLabel label;
    boolean launchNewWindow = false;

    StartScreen() throws Exception {
        // Opening image
        String directoryLocation = "C:\\Users\\ilias\\IdeaProjects\\Java Project\\src\\LearningJava\\MusicPlayer\\IMAGES\\MusicPlayerFrontPage.png";
        ImageIcon image = new ImageIcon(directoryLocation);
        Border border = BorderFactory.createLineBorder(new Color(0x396DA5), 5);

        // Setting up button
        button = new JButton();
        button.setBounds(175, 300, 150, 75);
        button.addActionListener(this);
        button.setText("Open MusicPlayer");
        button.setFocusable(false);
        button.setFont(new Font("Comic Sans", Font.BOLD, 15));
        button.setIconTextGap(-15);
        button.setForeground(Color.white);
        button.setBackground(new Color(0x396DA5));
        button.setBorder(BorderFactory.createEtchedBorder());

        // Setting up the label
        label = new JLabel();
        label.setText("Music made simple");
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(new Color(0x396DA5));
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));
        label.setIconTextGap(-100);
        label.setBackground(Color.black);
        label.setOpaque(true);
        label.setBorder(border);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setResizable(false);
        this.add(button);
        this.add(label);
        this.setVisible(true);

        while(launchNewWindow == false){
            Thread.sleep(100);
        }
        this.dispose();
        new MusicPlayerClass();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            launchNewWindow = true;
        }
    }


}

