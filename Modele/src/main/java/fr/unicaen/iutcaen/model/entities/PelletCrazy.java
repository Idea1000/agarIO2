package fr.unicaen.iutcaen.model.entities;

import fr.unicaen.iutcaen.model.Point;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

public class PelletCrazy extends Pellet{
    public PelletCrazy(int id, Point position, double mass, Color color) {
        super(id, position, mass, color);
    }

    @Override
    public void applyEffect(Cell cell) {
        Random r = new Random();
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(33), event -> {
            cell.setColor(new Color(r.nextDouble(),r.nextDouble(), r.nextDouble(), 0.0));
        }));
        if (cell == null){
            timeline.stop();
        }
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
}
