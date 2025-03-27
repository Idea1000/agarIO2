package fr.unicaen.iutcaen.launcher;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import fr.unicaen.iutcaen.view.Game;
import javafx.event.ActionEvent;

public class LauncherController {

    @FXML
    private Button local;

    @FXML
    private Button online;

    @FXML
    private TextField nickname;

    @FXML
    private void onLocal(ActionEvent event) {
    	Game.startGame(true);
    }

    @FXML
    private void onOnLine(ActionEvent event) {
    	Game.startGame(false);
    }
}