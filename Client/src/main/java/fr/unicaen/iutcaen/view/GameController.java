package fr.unicaen.iutcaen.view;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryPellet;
import fr.unicaen.iutcaen.model.quadtree.QuadTree;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private Pane worldPane;

    // Variables qui étaient dans HelloApplication
    private double mX, mY;
    private List<Entity> entities;
    private Player p;
    private Point vector;
    private boolean space = false;

    private QuadTree quadTree;
    private Timeline timeline;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configuration de base de la "carte" (Pane)
        worldPane.setMinSize(Config.WORLD_WIDTH, Config.WORLD_HEIGHT);

        worldPane.setPrefSize(10000.0,10000.0);
        // Création d'un QuadTree pour gérer les entités
        quadTree = new QuadTree(new Boundary(0, 0, Config.WORLD_WIDTH, Config.WORLD_HEIGHT), 0);

        // Ex. d'insertion d'un Pellet au centre
        FactoryPellet factoryPellet = new FactoryPellet();
        quadTree.insert(factoryPellet.fabrique(
                new Point(Config.WORLD_WIDTH / 2.0, Config.WORLD_HEIGHT / 2.0),
                2, Color.BLACK
        ));

        // Création du joueur
        p = new Player(
                new Point(Config.WORLD_WIDTH / 2.0, Config.WORLD_HEIGHT / 2.0),
                100,
                Color.RED
        );
        // Vue associée au joueur
        new PlayerView(p, worldPane);

        // Gestion de la souris : on récupère la position pour déplacer le joueur
        worldPane.setOnMouseMoved(mouseEvent -> {
            mX = mouseEvent.getX();
            mY = mouseEvent.getY();
            // Le vecteur de déplacement pointe de la cellule vers la souris
            vector = new Point(mX - p.getCenter().getX(), mY - p.getCenter().getY());
        });

        // Gestion du clavier (ex. barre espace pour "split")
        worldPane.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE && !space) {
                p.split();
                space = true;
            }
        });
        worldPane.setOnKeyReleased(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.SPACE) {
                space = false;
            }
        });

        // Timeline pour rafraîchir (environ 30 fois/s)
        timeline = new Timeline(new KeyFrame(Duration.millis(33), event -> {
            // Récupère les entités dans la zone visible autour du joueur
            entities = quadTree.query(new Boundary(
                    p.getPosition().getX() - Config.SCREEN_WIDTH / 2.0,
                    p.getPosition().getY() - Config.SCREEN_HEIGHT / 2.0,
                    Config.SCREEN_WIDTH,
                    Config.SCREEN_HEIGHT
            ));

            // Affiche les pellets trouvés
            if (entities != null) {
                for (Entity entity : entities) {
                    if (entity instanceof Pellet) {
                        new PelletView((Pellet) entity, worldPane);
                    }
                }
            }

            // Déplacement du joueur
            if (vector != null) {
                p.moveWithvector(vector);
            }

            // Déplace la "caméra" pour centrer sur le joueur
            worldPane.setTranslateX(Config.SCREEN_WIDTH / 2.0 - p.getPosition().getX());
            worldPane.setTranslateY(Config.SCREEN_HEIGHT / 2.0 - p.getPosition().getY());
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Pour que les événements clavier fonctionnent immédiatement
        worldPane.requestFocus();
    }
}
