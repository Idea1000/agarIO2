package fr.unicaen.iutcaen.launcher;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.World;
import fr.unicaen.iutcaen.network.Client;
import fr.unicaen.iutcaen.view.Game;
import fr.unicaen.iutcaen.view.Game2;
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
        Player p = new Player(new Point(Config.WORLD_WIDTH / 2.0, Config.WORLD_HEIGHT / 2.0), 100, Color.RED);
        World world = World.getInstence();
        world.addPlayer(p); 
    	Game2.startGame(null, world, p, true);
    }

    @FXML
    private void onOnLine(ActionEvent event) {
    	Client client = new Client();
        client.launche();
    }
}