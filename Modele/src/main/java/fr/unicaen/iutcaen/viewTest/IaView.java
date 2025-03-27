package fr.unicaen.iutcaen.viewTest;

import fr.unicaen.iutcaen.ai.AI;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.entities.Cell;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class IaView {
    private AI ia;
    public IaView(AI ia, Pane root) {
        this.ia = ia;
        for (Cell cell : ia.getCells().getAllCells()) {
            Circle c = new Circle();
            SimpleDoubleProperty p = new SimpleDoubleProperty(cell.getPosition().getX());
            c.centerXProperty().bind(cell.getPosition().xProperty());
            c.centerYProperty().bind(cell.getPosition().yProperty());
            c.radiusProperty().bindBidirectional(new SimpleDoubleProperty(cell.getSize()));
            c.setFill(cell.getColor());
            root.getChildren().add(c);
        }

    }
}
