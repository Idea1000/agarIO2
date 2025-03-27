package fr.unicaen.iutcaen.viewTest;



import fr.unicaen.iutcaen.ai.RandomMovementAI;
import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.*;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;

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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.List;


public class HelloApplication extends Application {


    private double mX, mY;
    private List<Entity> entities;
    private Player p;
    private Point vector;

    private Pane worldPane;

    private SimpleDoubleProperty scaleProperty = new SimpleDoubleProperty(1);

    @Override

    public void start(Stage stage) {
        worldPane = new Pane();
        worldPane.setMinSize(Config.MAP_WIDTH, Config.MAP_HEIGHT);

        Scene scene = new Scene(worldPane, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        scaleProperty.addListener((obs, old, newV) -> {

            System.out.println("Scale: " + newV);
            System.out.println("PlayerX: " + p.getPosition().getX() + ", PlayerY: " + p.getPosition().getY());
            System.out.println("TranslateX: " + worldPane.getTranslateX() + ", TranslateY: " + worldPane.getTranslateY());
            System.out.println("scene width: " + worldPane.getScene().getWidth() + ", scene height: " + worldPane.getScene().getHeight());
            System.out.println("pane width: " + worldPane.getWidth() + ", pane height: " + worldPane.getHeight());
        });

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

        p = new Player(new Point(Config.MAP_WIDTH / 2.0, Config.MAP_HEIGHT / 2.0), 100, Color.RED);
        //p = new Player(new Point(200.0,200.0), 100, Color.RED);
        PlayerView pv = new PlayerView(p, worldPane);
        World world = World.getInstence();
        world.addPlayer(p);


        WorldView worldView = new WorldView(p, worldPane);
        /*p.getCells().getMassProperty().addListener((observableValue, number, t1) -> {
            System.out.println("c le listener");
                worldPane.setScaleX(10 * Math.sqrt(p.getCells().getDiameter()));
                worldPane.setScaleY(10 * Math.sqrt(p.getCells().getDiameter()));
            }
        );*/
        worldPane.setBackground(new Background(new BackgroundFill(Color.ALICEBLUE, CornerRadii.EMPTY, Insets.EMPTY)));

        HashMap<Entity, AbstractView> linkModelView = new HashMap<>();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(33), event -> {
            double scale = 10 / Math.sqrt(p.getCells().getDiameter());
//            //double scale = 0.90;
////
            worldPane.setScaleX(scale);
            worldPane.setScaleY(scale);
//            System.out.println(worldPane.getScaleX());
            HashMap<Cell, List<Entity>> eatings = new HashMap<>();
//            double positionCameraX = p.getPosition().getX() - Config.SCREEN_WIDTH /2.0;
//            double positionCameraY = p.getPosition().getY() - Config.SCREEN_HEIGHT /2.0;
            double positionCameraX = -(( worldPane.getScene().getWidth() / 2.0 - p.getPosition().getX() ) * scale);
            double positionCameraY = -(( worldPane.getScene().getHeight() / 2.0 - p.getPosition().getY() ) * scale);
            entities = World.getInstence().getEntitiesAround(new Boundary(positionCameraX-200, positionCameraY-200, (worldPane.getScene().getWidth()/scale)+500, (worldPane.getScene().getHeight()/scale)+500));
            for (Entity entity : entities) {
                if (!linkModelView.containsKey(entity)) {
                    if (entity instanceof Pellet) {
                        PelletView pelletView = new PelletView((Pellet) entity, worldPane);
                        linkModelView.put(entity, pelletView);
                    }
                }
            }

            if (vector != null)
                p.moveWithvector(vector);

            Point newPos = p.getPosition();
            for (Entity entity : entities) {
                if (p.absorb(entity)) {
                    if (linkModelView.get(entity) != null) {
                        linkModelView.get(entity).delete(worldPane);
                    }

                }
            }


            // Move the entire game world (simulating a camera)
            worldPane.setTranslateX((( worldPane.getScene().getWidth() / 2.0 - newPos.getX() ) * scale));
            worldPane.setTranslateY((( worldPane.getScene().getHeight() / 2.0 - newPos.getY() )* scale));

//            gameRoot.setTranslateX((100)-newPos.getX());
//            gameRoot.setTranslateY((100)-newPos.getY());
            scaleProperty.set(scale);
        }));


        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
