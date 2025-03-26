
package fr.unicaen.iutcaen.view;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static final int HORIZONTAL = 320;
    public static final int VERTICAL = 320;

    /**
     * Starts the window
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/fr/unicaen/iutcaen/agario2/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HORIZONTAL, VERTICAL);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}