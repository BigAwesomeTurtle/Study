# PianoSimulator

A simple piano simulator written in Java with JavaFX GUI.
### Program description
**music**

In this folder you can find the notes that are used in the program.

- **core:**
	- **Sound.java:**
A class used to play audio files. It contains a single static method **playMusic**  that takes the path to the audio file and plays the sound if the path is correct.

- **gui:**
	- **Sample.java:**
Used to create the main application window with a scene that is described in the sample.fxml.
	-	**sample.fxml**
A markup file that creates all the necessary GUI elements and defines their appearance.

-	**controller:**
	- **Controller.java:**
Controller class for sample.fxml file. Used for application reaction to user actions. Using the **playMusic** method defined in the **Sound.java**  class, it starts playing a specific sound file when you click a button in the application or on the keyboard.
### Appearance of the program
<img src="Piano_Simulator/screens/screen.png" width="700">
