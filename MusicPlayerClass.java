import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;


public class MusicPlayerClass extends JFrame implements ChangeListener, ActionListener, MouseListener {
    // Global variables
    File file;
    JButton playButton, stopButton, resetButton, muteButton;
    JSlider volumeSlider;
    JComboBox songList;
    JLabel imageLabel = new JLabel();
    JLabel headerLabel = new JLabel();
    JPanel mouseClickDetectionLabel = new JPanel();
    JProgressBar bar = new JProgressBar();
    JMenuBar menu = new JMenuBar();
    JMenuItem colorStyle, backgroundImage;
    Color primarySelectedColor = new Color(0x396DA5);
    Color secondarySelectedColor = Color.BLACK;

    Clip clip;
    String[] songs;
    String songName;
    float volumeLevel = -40.0f;
    int previousVolumeLevel = 0;
    boolean isPlaying = false;
    boolean firstCall = true;

    // Add the path to the folder
    String backgroundImageDirectory = "";
    String songListPath = "";


    // Buttons
    public void setMusicButtons(){
        // Setting up the play button
        playButton = new JButton();
        playButton.setBounds(67, 400, 100, 50);
        playButton.addActionListener(this);
        playButton.addMouseListener(this);
        playButton.setText("►");
        playButton.setFocusable(false);
        playButton.setFont(new Font("Comic Sans", Font.BOLD, 60));
        playButton.setForeground(Color.black);
        playButton.setBackground(primarySelectedColor);
        playButton.setBorder(BorderFactory.createEtchedBorder());

        // Setting up the stop button
        stopButton = new JButton();
        stopButton.setBounds(217, 400, 100, 50);
        stopButton.addActionListener(this);
        stopButton.addMouseListener(this);
        stopButton.setText("❚❚");
        stopButton.setFocusable(false);
        stopButton.setFont(new Font("Comic Sans", Font.BOLD, 30));
        stopButton.setForeground(Color.black);
        stopButton.setBackground(primarySelectedColor);
        stopButton.setBorder(BorderFactory.createEtchedBorder());

        // Setting up the reset button
        resetButton = new JButton();
        resetButton.setBounds(367, 400, 100, 50);
        resetButton.addActionListener(this);
        resetButton.addMouseListener(this);
        resetButton.setText("\uD83D\uDDD8");
        resetButton.setFocusable(false);
        resetButton.setFont(new Font("Comic Sans", Font.BOLD, 35));
        resetButton.setForeground(Color.black);
        resetButton.setBackground(primarySelectedColor);
        resetButton.setBorder(BorderFactory.createEtchedBorder());

        // Setting up the mute button
        muteButton = new JButton();
        muteButton.setBounds(480, 320, 30, 30);
        muteButton.addActionListener(this);
        muteButton.addMouseListener(this);
        muteButton.setText("\uD83D\uDD07");
        muteButton.setFocusable(false);
        muteButton.setFont(new Font("Comic Sans", Font.BOLD, 20));
        muteButton.setIconTextGap(-15);
        muteButton.setForeground(Color.black);
        muteButton.setBackground(primarySelectedColor);
        muteButton.setBorder(BorderFactory.createEtchedBorder());

    }

    //Menu
    public void setMenuBar(){
        menu.setBackground(secondarySelectedColor);
        menu.setLayout(new FlowLayout());

        colorStyle = new JMenuItem("Color_Style");
        colorStyle.setForeground(Color.WHITE);
        colorStyle.setBackground(new Color(0x363636));
        colorStyle.setHorizontalAlignment(JMenuItem.CENTER);
        colorStyle.addActionListener(this);

        backgroundImage = new JMenuItem("Background_Image");
        backgroundImage.setForeground(Color.WHITE);
        backgroundImage.setBackground(new Color(0x363636));
        backgroundImage.setHorizontalAlignment(JMenuItem.CENTER);
        backgroundImage.addActionListener(this);

        // Appending colorStyle and backgroundImage to the menu
        menu.add(colorStyle);
        menu.add(backgroundImage);
    }

    // Bar
    public void setBar() throws Exception {
        // Creating bar
        bar.setMinimum(0);
        bar.setMaximum(getDuration(file));
        bar.setValue(0);
        bar.setBounds(25,365,500,25);
        bar.setStringPainted(true);
        bar.setString(songName);
        bar.setFont(new Font("Arimo",Font.BOLD,25));
        bar.setForeground(primarySelectedColor);
        bar.setBackground(secondarySelectedColor);

        //Creating label to detect mouseclick position on the bar
        mouseClickDetectionLabel.setBounds(25,365,500,25);
        mouseClickDetectionLabel.addMouseListener(this);
        mouseClickDetectionLabel.setVisible(true);
    }

    // Background
    public void setBackgroundImage(){
        ImageIcon image = new ImageIcon(
                backgroundImageDirectory);
        imageLabel.setIcon(image);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    // Song list
    public void setSongList(){
        songList = new JComboBox(songs);
        songList.addActionListener(this);
        songList.setBounds(50, 5, 425, 55);
        songList.setBackground(new Color(0x79000000, true));
        songList.setForeground(primarySelectedColor);
        songList.setFont(new Font("Helvetica", Font.BOLD, 20));
        songList.setSelectedIndex(0);
    }

    // Volume slider
    public void setVolumeSlider(){
        volumeSlider = new JSlider(-80, 0, -40);
        volumeSlider.addChangeListener(this);
        volumeSlider.setOrientation(SwingConstants.VERTICAL);
        volumeSlider.setOpaque(false);
        volumeSlider.setBounds(510, 260, 15, 100);
    }

    // Loading songs from directory
    public void loadSongs(){
        // Filling songs with a list of the songs from songListPath
        File fileDirectoryPathOfSongs = new File(songListPath);
        songs = fileDirectoryPathOfSongs.list();
        for(int i=0; i<songs.length; i++){
            // Removing .wav (4 characters) from each of their names
            songs[i] = songs[i].substring(0, songs[i].length() - 4);
        }
    }

    // Launch song clip
    public void launchClip() throws Exception {
        // Set up song directory
        System.out.println("Music player called");
        File gettingFirstSong = new File(songListPath);
        String[] listOfSongs = gettingFirstSong.list();
        String path;

        // If launchClip() is being called for the first time, select the first song of the directory, else select the songName
        if(firstCall) {
            songName = listOfSongs[0];
            path = songListPath + "\\" + songName;

        }else{
            path = songListPath + "\\" + songName + ".wav";

        }
        file = new File(path);



        // Set up audio
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);

        // Set up volume
        FloatControl gainControl =
                (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volumeLevel); // Reduce volume from volume bar

        // After calling launchClip() the first time firstCall is turned to false
        firstCall = false;
    }

    MusicPlayerClass() throws Exception {
        // Load songs
        loadSongs();

        // Launches first clip (of songName)
        launchClip();

        // Launches and displays the songList
        setSongList();

        // Displays the song bar
        setBar();

        // Sets menu bar
        setMenuBar();

        // Sets volume slider
        setVolumeSlider();

        // Sets buttons responsible for playing, pausing and restarting a song
        setMusicButtons();


        // Sets the initial background image
        setBackgroundImage();


        // Prepares the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.add(bar);
        this.add(playButton);
        this.add(stopButton);
        this.add(resetButton);
        this.add(volumeSlider);
        this.add(muteButton);
        this.add(songList);
        this.add(mouseClickDetectionLabel);
        this.setJMenuBar(menu);
        this.setSize(550,525);
        this.setResizable(false);

        this.add(headerLabel, BorderLayout.NORTH);
        this.add(imageLabel, BorderLayout.CENTER);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Fills the bar
        fill();

    }

    // Filling the progress bar
    public void fill() throws Exception {

        while(true) {
            // Skipping if isPlaying is False
            while(isPlaying == false){
                Thread.sleep(100);
            }

            // Displaying minute and second of clip on the bar and filling it accordingly
            System.out.println(clip.getMicrosecondPosition() /1000000);
            bar.setValue((int)(clip.getMicrosecondPosition() /1000000));
            if(bar.getValue()%60 < 10){
                bar.setString(bar.getValue() / 60 + ":0" + bar.getValue()%60);
            }else{
                bar.setString(bar.getValue() / 60 + ":" + bar.getValue()%60);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    // Getting length of song
    private static int getDuration(File file) throws Exception {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        AudioFormat format = audioInputStream.getFormat();
        long audioFileLength = file.length();
        int frameSize = format.getFrameSize();
        float frameRate = format.getFrameRate();
        float durationInSeconds = (audioFileLength / (frameSize * frameRate));
        return (int)(durationInSeconds);
    }

    // Volume bar actions
    @Override
    public void stateChanged(ChangeEvent e) {
        // Reduce volume from menu bar
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeLevel = Float.parseFloat(String.valueOf(volumeSlider.getValue()));
        gainControl.setValue(volumeLevel);

        System.out.println(volumeSlider.getValue());

    }

    // Button actions
    @Override
    public void actionPerformed(ActionEvent e) {
        // Starting clip is playButton is clicked
        if(e.getSource() == playButton){
            isPlaying = true;
            clip.start();
        }
        // Stopping the clip if the stopButton is clicked
        else if(e.getSource() == stopButton){
            isPlaying = false;
            clip.stop();
        }
        // Setting clip time to the start when the resetButton is clicked
        else if(e.getSource() == resetButton){
            clip.setMicrosecondPosition(0);
            bar.setValue(0);
            bar.setString("0:00");
        }
        // Changing the song playing (closing current clip and opening new one)
        else if(e.getSource() == songList){
            songName = (String) songList.getSelectedItem();
            System.out.println("About to play " + songName);

            try {
                clip.close();
                launchClip();

                setBar();

            } catch (Exception exception) {
                exception.printStackTrace();
            }

        }
        // Mutes the song if muteButton is clicked
        else if(e.getSource() == muteButton){
            System.out.println("MUTE");
            if(volumeSlider.getValue() != -80){
                previousVolumeLevel = volumeSlider.getValue();
                volumeSlider.setValue(-80);
            }else{
                volumeSlider.setValue(previousVolumeLevel);
            }
        }
        // Displays two JColorChoosers so the user selects the two colours to make up the colour theme
        else if(e.getSource() == colorStyle){
            primarySelectedColor = JColorChooser.showDialog(null, "Pick first color", Color.black);
            secondarySelectedColor = JColorChooser.showDialog(null, "Pick second color", Color.black);
            if(primarySelectedColor != null && secondarySelectedColor != null) {
                playButton.setBackground(primarySelectedColor);
                stopButton.setBackground(primarySelectedColor);
                resetButton.setBackground(primarySelectedColor);
                muteButton.setBackground(primarySelectedColor);
                bar.setForeground(primarySelectedColor);
                bar.setBackground(secondarySelectedColor);
                menu.setBackground(secondarySelectedColor);
                songList.setForeground(primarySelectedColor);
            }

        }
        // Changes background image if backgroundImage is clicked
        else if(e.getSource()==backgroundImage) {

            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setCurrentDirectory(new File(backgroundImageDirectory));

            int response = fileChooser.showOpenDialog(null);

            if(response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                String path = file.getPath();
                ImageIcon image = new ImageIcon(path);
                imageLabel.setIcon(image);
                System.out.println(path);
            }
        }
    }


    //Mouse actions
    @Override
    public void mouseEntered(MouseEvent e) {
        // Changing colour of the buttons when the mouse enters them
        if(e.getSource() == playButton){
            playButton.setBackground(new Color(0x84F8E5));
        }else if(e.getSource() == stopButton){
            stopButton.setBackground(new Color(0x84F8E5));
        }else if(e.getSource() == resetButton){
            resetButton.setBackground(new Color(0x84F8E5));
        }else if(e.getSource() == muteButton) {
            muteButton.setBackground(new Color(0x84F8E5));
        }
    }
    @Override
    public void mouseExited(MouseEvent e) {
        // Changes colour back to the original colour when the mouse exits the buttons
        if(e.getSource() == playButton){
            playButton.setBackground(primarySelectedColor);
        }else if(e.getSource() == stopButton){
            stopButton.setBackground(primarySelectedColor);
        }else if(e.getSource() == resetButton){
            resetButton.setBackground(primarySelectedColor);
        } else if(e.getSource() == muteButton){
            muteButton.setBackground(primarySelectedColor);
        }

    }
    @Override
    public void mousePressed(MouseEvent e) {
        // Make buttons a special colour temporarily when clicked so the user knows they've been clicked
        if(e.getSource() == playButton){
            playButton.setBackground(new Color(0xC6D4F6));
        }else if(e.getSource() == stopButton){
            stopButton.setBackground(new Color(0xC6D4F6));
        }else if(e.getSource() == resetButton){
            resetButton.setBackground(new Color(0xC6D4F6));
        }
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        // Sets colour back to the colour the buttons are when the mouse is on them, after being clicked on
        if(e.getSource() == playButton){
            playButton.setBackground(new Color(0x84F8E5));
        }else if(e.getSource() == stopButton){
            stopButton.setBackground(new Color(0x84F8E5));
        }else if(e.getSource() == resetButton){
            resetButton.setBackground(new Color(0x84F8E5));
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        // Setting clip time to position in bar clicked
        if(e.getSource() == mouseClickDetectionLabel){
            int x = e.getX();
            try {
                clip.setMicrosecondPosition(1000000 * (getDuration(file) * x / 500));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

}
