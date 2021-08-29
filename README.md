# MusicPlayer
A java program which serves as a music player interface. Capable of: <br>

>1. Playing, pausing, restarting a list of songs (of a .wav format) in a specific directory. 
>2. Changing the colour theme.
>3. Changing volume of songs.
>4. Changing the background.

<h1>Basic setup</h1>

Make sure you have these two folders:
>1. A folder with at least one .gif , .jpg or .png file(s) to use as backgrounds.
>2. A folder where you keep your .wav songs (which can be downloaded using any .wav converter found online).

In ***StartScreen.java*** set *directoryLocation (line 16)* to the directory of the image you want displayed on the starting screen.<br>

In ***MusicPlayerClass.java*** set *backgroundImageDirectory (line 39)* to your path of the background image you want to use.

In ***MusicPlayerClass.java*** set *songListPath (line 40)* to the directory of the folder of the .wav songs you have downloaded.

<h1>How to use it</h1>

- On the buttom of the screen there is a play, pause and restart button.<br>

- On the right of the screen there is a volume slider and a mute button, used to adjusted the volume.<br>

- To select a specific moment of a song click on the bar (position relative to start and end of the bar corresponds to the position of the song selected).<br>

- On the top of the screen there is the list of all the songs in your song directory, click on whichever song you want to listen to.<br>

- In the menu on the very top of the screen there is the *Color_Style* button which prompts you to enter two colours to use as a colour theme.<br>

- Also found in the menu is the *Background_Image* button which promts you to select a file to use as a background.
