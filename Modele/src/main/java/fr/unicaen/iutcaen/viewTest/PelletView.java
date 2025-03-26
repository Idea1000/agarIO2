package fr.unicaen.iutcaen.viewTest;

import fr.unicaen.iutcaen.ai.IA;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class PelletView {

    private Pellet p;
    public PelletView(Pellet p, Pane root) {
        this.p = p;
        Circle c = new Circle();
        SimpleDoubleProperty pos = new SimpleDoubleProperty(p.getPosition().getX());
        c.centerXProperty().bind(p.getPosition().xProperty());
        c.centerYProperty().bind(p.getPosition().yProperty());
        c.radiusProperty().bindBidirectional(new SimpleDoubleProperty(p.getSize()));
        c.setFill(p.getColor());
        root.getChildren().add(c);
    }
}
