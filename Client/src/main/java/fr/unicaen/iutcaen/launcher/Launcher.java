package fr.unicaen.iutcaen.launcher;

import fr.unicaen.iutcaen.view.Game;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launcher extends Application {

    public static final int HORIZONTAL = 515;
    public static final int VERTICAL = 277;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Affichage du menu de sélection (local ou en ligne)
        // Création d'une instance World et GameView puis démarrage du jeu

        FXMLLoader fxmlLoader = new FXMLLoader(Game.class.getResource("/fr/unicaen/iutcaen/launcher.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HORIZONTAL, VERTICAL);
        primaryStage.setTitle("AgarIO 2");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}