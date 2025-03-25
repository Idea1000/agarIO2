package fr.unicaen.iutcaen.view;

import fr.unicaen.iutcaen.ai.IA;
import fr.unicaen.iutcaen.ai.RandomMovementAI;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.CellPack;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    public static final int HORIZONTAL = 320;
    public static final int VERTICAL = 320;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/fr/unicaen/iutcaen/agario2/hello-view.fxml"));
        Pane main = new Pane();
        test(main);
        Scene scene = new Scene(main, 1500, 900);
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

        Player p = new Player(new Point(200.0,200.0), 100, Color.RED);
        //test louison ia
        IA random = new IA(new Point(200.0,200.0), 100, Color.RED);
        random.setBehavior(new RandomMovementAI());
        //IA randomIA = new RandomMovementAI(new Point(200.0,200.0), 100, Color.RED);
        PlayerView pv = new PlayerView(p, root);
        for (Cell allCell : p.getCells().getAllCells()) {
            System.out.println(allCell.getPosition());
        }
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(33), event -> {
                    p.movePlayer(new Point(mX, mY));
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        for (Cell allCell : p.getCells().getAllCells()) {
            System.out.println(allCell.getPosition());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}