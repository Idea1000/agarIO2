package fr.unicaen.iutcaen.viewTest;



import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.*;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Node;
import javafx.scene.Scene;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Cell;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.util.HashMap;

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
        Scene scene = new Scene(worldPane, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        stage.setResizable(false);

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
        PlayerView pv = new PlayerView(p, worldPane);

        World world = World.getInstence();
        world.addPlayer(p);


        WorldView worldView = new WorldView(p, worldPane);

        HashMap<Entity, AbstractView> linkModelView = new HashMap<>();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(Config.UPDATE), event -> {
            double scale = 5 / Math.sqrt(p.getCells().getSize());
            worldPane.setScaleX(scale);
            worldPane.setScaleY(scale);

            HashMap<Cell, List<Entity>> eatings = new HashMap<>();

            double positionCameraX = p.getPosition().getX() - worldPane.getWidth();
            double positionCameraY = p.getPosition().getY() - worldPane.getHeight();
//            double positionCameraX = -(( worldPane.getWidth() / 2.0 - p.getPosition().getX() ) * scale);
//            double positionCameraY = -(( worldPane.getHeight() / 2.0 - p.getPosition().getY() ) * scale);
            entities = World.getInstence().getEntitiesAround(new Boundary((positionCameraX-500), (positionCameraY-500), (worldPane.getWidth()/scale)+1000, (worldPane.getHeight()/scale)+1000));
            //System.out.println("width : " + worldPane.getWidth());
            //System.out.println("height : " + worldPane.getHeight());
//            System.out.println("nb entites within boundary : " + entities.size());
            for (Entity entity : entities) {
                if (!linkModelView.containsKey(entity)) {
                    if (entity instanceof Pellet) {
                        PelletView pelletView = new PelletView((Pellet) entity, worldPane);
                        linkModelView.put(entity, pelletView);
                    }
                }
            }

            worldPane.setTranslateX(( worldPane.getWidth() / 2.0 - p.getPosition().getX() ) * scale);
            worldPane.setTranslateY(( worldPane.getHeight() / 2.0 - p.getPosition().getY() ) * scale);
            scaleProperty.set(scale);

            if (vector != null)
                p.moveWithvector(vector);

            for (Entity entity : entities) {
                if (p.absorb(entity)) {
                    if (linkModelView.get(entity) != null) {
                        linkModelView.get(entity).delete(worldPane);
                    }

                }
            }
        }));


        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
