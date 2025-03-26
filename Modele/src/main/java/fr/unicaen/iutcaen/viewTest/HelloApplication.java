package fr.unicaen.iutcaen.viewTest;

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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class HelloApplication extends Application {
    public static final int WORLD_WIDTH = 10000;
    public static final int WORLD_HEIGHT = 10000;
    public static final int SCREEN_WIDTH = 1500;
    public static final int SCREEN_HEIGHT = 900;

    private double mX, mY;
    private List<Entity> entities;
    private Group gameRoot;
    private Player p;
    private Point vector;

    @Override
    public void start(Stage stage) {
        gameRoot = new Group();

        Pane worldPane = new Pane();
        worldPane.setMinSize(WORLD_WIDTH, WORLD_HEIGHT);
        gameRoot.getChildren().add(worldPane);

        Scene scene = new Scene(gameRoot, SCREEN_WIDTH, SCREEN_HEIGHT);

        setupGame(worldPane);

        stage.setTitle("Agar.io Clone");
        stage.setScene(scene);
        stage.show();
    }

    private void setupGame(Pane worldPane) {
        worldPane.setOnMouseMoved(mouseEvent -> {
            mX = mouseEvent.getX();
            mY = mouseEvent.getY();
            vector = new Point(mX - p.getCenter().getX(), mY - p.getCenter().getY());
        });

        QuadTree quadTree = new QuadTree(new Boundary(0, 0, WORLD_WIDTH, WORLD_HEIGHT), 0);
        FactoryPellet factoryPellet = new FactoryPellet();
        quadTree.insert(factoryPellet.fabrique(new Point(WORLD_WIDTH / 2.0, WORLD_HEIGHT / 2.0), 2, Color.BLACK));

        p = new Player(new Point(WORLD_WIDTH / 2.0, WORLD_HEIGHT / 2.0), 100, Color.RED);
        PlayerView pv = new PlayerView(p, worldPane);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(33), event -> {
            entities = quadTree.query(new Boundary(p.getPosition().getX() - SCREEN_WIDTH / 2.0, p.getPosition().getY() - SCREEN_HEIGHT / 2.0, SCREEN_WIDTH, SCREEN_HEIGHT));

            for (Entity entity : entities) {
                if (entity instanceof Pellet) new PelletView((Pellet) entity, worldPane);
            }

            if (vector != null)
                moveWithvector(p,vector);

            Point newPos = p.getPosition();

            worldPane.setBackground(new Background(new BackgroundFill(Color.AQUA, CornerRadii.EMPTY, Insets.EMPTY)));

            // Move the entire game world (simulating a camera)
            gameRoot.setTranslateX(SCREEN_WIDTH / 2.0 - newPos.getX());
            gameRoot.setTranslateY(SCREEN_HEIGHT / 2.0 - newPos.getY());
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void moveWithvector(Player p, Point vector){
        p.movePlayer(new Point(p.getCenter().getX() + vector.getX(), p.getCenter().getY() + vector.getY()));
    }
}
