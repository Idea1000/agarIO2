package fr.unicaen.iutcaen.viewTest;



import fr.unicaen.iutcaen.ai.RandomMovementAI;
import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryPellet;
import fr.unicaen.iutcaen.model.quadtree.QuadTree;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.List;


public class HelloApplication extends Application {


    private double mX, mY;
    private List<Entity> entities;
    private Group gameRoot;
    private Player p;
    private Point vector;

    @Override

    public void start(Stage stage) {
        gameRoot = new Group();

        Pane worldPane = new Pane();
        worldPane.setMinSize(Config.WORLD_WIDTH, Config.WORLD_HEIGHT);
        gameRoot.getChildren().add(worldPane);

        Scene scene = new Scene(gameRoot, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        setupGame(worldPane);

        stage.setTitle("Agar.io Clone");
        stage.setScene(scene);
        stage.show();
        worldPane.requestFocus();
    }

    private boolean space = false;
    private void setupGame(Pane worldPane) {
        worldPane.setOnMouseMoved(mouseEvent -> {
            mX = mouseEvent.getX();
            mY = mouseEvent.getY();
            vector = new Point(mX - p.getCenter().getX(), mY - p.getCenter().getY());
        });

        worldPane.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE){
                if (!space){
                    System.out.println("space");
                    p.split();
                    space = true;
                }
            }
        });
        worldPane.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE){
                space = false;
            }
        });


        QuadTree quadTree = new QuadTree(new Boundary(0, 0, Config.WORLD_WIDTH, Config.WORLD_HEIGHT), 0);
        FactoryPellet factoryPellet = new FactoryPellet();
        quadTree.insert(factoryPellet.fabrique(new Point(Config.WORLD_WIDTH / 2.0, Config.WORLD_HEIGHT / 2.0), 2, Color.BLACK));

        p = new Player(new Point(Config.WORLD_WIDTH / 2.0, Config.WORLD_HEIGHT / 2.0), 100, Color.RED);
        PlayerView pv = new PlayerView(p, worldPane);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(33), event -> {
            entities = quadTree.query(new Boundary(p.getPosition().getX() - Config.SCREEN_WIDTH / 2.0, p.getPosition().getY() - Config.SCREEN_HEIGHT / 2.0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT));

            for (Entity entity : entities) {
                if (entity instanceof Pellet) new PelletView((Pellet) entity, worldPane);
            }

            if (vector != null)
                p.moveWithvector(vector);

            Point newPos = p.getPosition();

            worldPane.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));

            // Move the entire game world (simulating a camera)
            gameRoot.setTranslateX(Config.SCREEN_WIDTH / 2.0 - newPos.getX());
            gameRoot.setTranslateY(Config.SCREEN_HEIGHT / 2.0 - newPos.getY());
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }


}
