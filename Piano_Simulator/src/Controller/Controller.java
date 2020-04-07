package Controller;

import core.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Controller {
    private final Map<String, String> noteMap = createMap();
    private boolean inputShift = false;

    private Map<String, String> createMap() {
        Map<String, String> myMap = new HashMap<>();
        myMap.put("q", "До");
        myMap.put("w", "Ре");
        myMap.put("e", "Ми");
        myMap.put("r", "Фа");
        myMap.put("t", "Соль");
        myMap.put("y", "Ля");
        myMap.put("u", "Си");
        myMap.put("i", "До");
        myMap.put("o", "Ре");
        myMap.put("p", "Ми");
        myMap.put("a", "Фа");
        myMap.put("s", "Соль");
        myMap.put("d", "Ля");
        myMap.put("f", "Си");
        myMap.put("g", "До");
        myMap.put("h", "Ре");
        myMap.put("j", "Ми");
        myMap.put("k", "Фа");
        myMap.put("l", "Соль");
        myMap.put("z", "Ля");
        myMap.put("x", "Си");
        myMap.put("c", "До");
        myMap.put("v", "Ре");
        myMap.put("b", "Ми");
        myMap.put("n", "Фа");
        myMap.put("m", "Соль");
        myMap.put("comma", "Ля");
        myMap.put("period", "Си");
        myMap.put("slash", "До");
        myMap.put("digit1", "До#");
        myMap.put("digit2", "Ре#");
        myMap.put("digit3", "Фа#");
        myMap.put("digit4", "Соль#");
        myMap.put("digit5", "Ля#");
        myMap.put("digit6", "До#");
        myMap.put("digit7", "Ре#");
        myMap.put("digit8", "Фа#");
        myMap.put("digit9", "Соль#");
        myMap.put("digit0", "Ля#");
        myMap.put("bG", "До#");
        myMap.put("bH", "Ре#");
        myMap.put("bK", "Фа#");
        myMap.put("bL", "Соль#");
        myMap.put("bZ", "Ля#");
        myMap.put("bC", "До#");
        myMap.put("bV", "Ре#");
        myMap.put("bN", "Фа#");
        myMap.put("bM", "Соль#");
        myMap.put("bCOMMA", "Ля#");
        return myMap;
    }

    @FXML
    private Label noteLabel;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        String ident = ((Button) event.getSource()).idProperty().get();
        Sound.playMusic("music/" + ident + ".wav");
        noteLabel.setText(noteMap.get(ident));

    }

    @FXML
    private void handleKeyPressedAction(KeyEvent event) {
        String filePath;
        if (inputShift) {
            filePath = "music/b" + event.getCode().toString() + ".wav";
            if (new File(filePath).exists()) {
                Sound.playMusic(filePath);
                noteLabel.setText(noteMap.get("b" + event.getCode().toString()));
            }

        } else if (event.getCode() != KeyCode.SHIFT) {
            filePath = "music/" + event.getCode().toString().toLowerCase() + ".wav";
            if (new File(filePath).exists()) {
                Sound.playMusic(filePath);
                noteLabel.setText(noteMap.get(event.getCode().toString().toLowerCase()));
            }

        } else {
            inputShift = true;
        }


    }

    @FXML
    private void handleKeyReleasedAction(KeyEvent event) {
        if (event.getCode() == KeyCode.SHIFT) inputShift = false;
    }
}
