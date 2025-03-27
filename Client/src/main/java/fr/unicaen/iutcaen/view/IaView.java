package fr.unicaen.iutcaen.view;

import fr.unicaen.iutcaen.ai.AI;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.entities.Cell;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class IaView extends AbstractView{
    private AI ia;
    private Circle c;
    public IaView(AI ia, Pane root) {
        this.ia = ia;
        for (Cell cell : ia.getCells().getAllCells()) {
             c = new Circle();
            c.centerXProperty().bind(cell.getPosition().xProperty());
            c.centerYProperty().bind(cell.getPosition().yProperty());
            cell.getMassProperty().addListener((observableValue, number, t1) -> {
                cell.setMass(t1.doubleValue());
                c.setRadius(cell.getSize());

            });

            c.setRadius(cell.getSize());
            c.setFill(cell.getColor());

            root.getChildren().add(c);
        }

    }

    public void delete(Pane worldPane) {
        worldPane.getChildren().remove(c);
    }
}
