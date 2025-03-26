package fr.unicaen.iutcaen.viewTest;


//import fr.unicaen.iutcaen.ai.IA;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.beans.property.SimpleDoubleProperty;

import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.entities.Cell;
import fr.unicaen.iutcaen.model.entities.Pellet;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class PelletView {

    private Pellet pellet;
    public PelletView(Pellet pellet, Pane root) {
        this.pellet = pellet;

        Circle c = new Circle();
        c.centerXProperty().bind(pellet.getPosition().xProperty());
        c.centerYProperty().bind(pellet.getPosition().yProperty());
        c.radiusProperty().bindBidirectional(new SimpleDoubleProperty(pellet.getSize()));
        c.setFill(pellet.getColor());
        root.getChildren().add(c);

    }
}
