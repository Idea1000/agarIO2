package fr.unicaen.iutcaen.viewTest;

import fr.unicaen.iutcaen.ai.IA;
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
        IA randomIA = new IA(new Point(300.0,300.0), 150, Color.BLUE);
        ArrayList<Entity> test = new ArrayList<Entity>();

        // Test distance euclidienne lag ?
        Random r = new Random();
        for (int i = 0; i < 3; i++) {
            Pellet pe = new Pellet(i,
                    new Point(r.nextDouble(300, 1000), r.nextDouble(300, 1000)),
                    r.nextDouble(1, 3),
                    Color.color(r.nextInt(256)/255.0, r.nextInt(256)/255.0, r.nextInt(256)/255.0));
            PelletView pv = new PelletView(pe,root);
            test.add(pe);
        }
        test.add(p.getCells());

        randomIA.setEntitiesInRange(test);
        randomIA.setBehavior(new RandomMovementAI());
        IaView iv = new IaView(randomIA,root);
        //


        PlayerView pv = new PlayerView(p, root);
        AtomicInteger count = new AtomicInteger();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(33), event -> {
                    p.movePlayer(new Point(mX, mY));
                    randomIA.move();
                    if(count.get() > 5){
                        randomIA.update();
                        count.set(0);
                    }else{
                        count.set(count.addAndGet(1));
                    }
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

    }

    public static void main(String[] args) {
        launch();
    }
}