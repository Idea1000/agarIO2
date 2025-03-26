package fr.unicaen.iutcaen.viewTest;


import fr.unicaen.iutcaen.ai.RandomMovementAI;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloApplication extends Application {
    public static final int HORIZONTAL = 320;
    public static final int VERTICAL = 320;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/fr/unicaen/iutcaen/agario2/hello-view.fxml"));
        Pane main = new Pane();
        Scene scene = new Scene(main, 1500, 900);
        test(main);
        System.out.println(main.getWidth());
        System.out.println(main.getHeight());
        stage.setTitle("Hello!");
        stage.setScene(scene);

        stage.show();
    }

    private double mX;
    private double mY;

    private void test(Pane root){
        root.setOnMouseMoved(mouseEvent -> {
            mX = mouseEvent.getX();
            mY = mouseEvent.getY();
        });

        Player p = new Player(new Point(750.0,450.0), 25, Color.RED);
        WorldView worldv = new WorldView(p,root);
        //test louison ia
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(33), event -> {
                    p.movePlayer(new Point(mX, mY));
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }
}