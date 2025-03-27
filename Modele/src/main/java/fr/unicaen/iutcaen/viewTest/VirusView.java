package fr.unicaen.iutcaen.viewTest;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.entities.Virus;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class VirusView extends AbstractView {

    private Virus virus;
    private Circle c;

    public VirusView(Virus virus, Pane root) {
        this.virus = virus;

        // Création du cercle pour le virus
        Circle c = new Circle();
        c.centerXProperty().bind(virus.getPosition().xProperty());
        c.centerYProperty().bind(virus.getPosition().yProperty());
        c.radiusProperty().bind(new SimpleDoubleProperty(virus.getSize()));
        c.setFill(virus.getColor());
        root.getChildren().add(c);
        this.c = c;

        // Ajout des triangles autour du cercle
        double triangleSize = 10;  // Taille des triangles
        int numberOfTriangles = 12;  // Nombre de triangles autour du cercle
        for (int i = 0; i < numberOfTriangles; i++) {
            double angle = 2 * Math.PI * i / numberOfTriangles;
            double x = Math.cos(angle) * (virus.getSize() + triangleSize);
            double y = Math.sin(angle) * (virus.getSize() + triangleSize);

            Polygon triangle = new Polygon();
            triangle.getPoints().addAll(
                    x, y,
                    x - triangleSize / 2 * Math.cos(angle - Math.PI / 4),
                    y - triangleSize / 2 * Math.sin(angle - Math.PI / 4),
                    x - triangleSize / 2 * Math.cos(angle + Math.PI / 4),
                    y - triangleSize / 2 * Math.sin(angle + Math.PI / 4)
            );
            triangle.setFill(Color.DARKRED);  // Couleur des triangles

            // Lier la position des triangles au centre du virus
            triangle.layoutXProperty().bind(virus.getPosition().xProperty());
            triangle.layoutYProperty().bind(virus.getPosition().yProperty());
            root.getChildren().add(triangle);
        }
    }

    @Override
    public void delete(Pane root) {
        root.getChildren().remove(c);
    }
}
