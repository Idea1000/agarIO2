package fr.unicaen.iutcaen.model;

import javafx.geometry.Point2D;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class World {
    private List<Player> players;
    private List<Pellet> pellets;
    private QuadTree quadTree;

    public World() {
        // Initialisation des listes et du QuadTree selon la taille de la carte

        players = new ArrayList<Player>();
        pellets = new ArrayList<Pellet>();
        quadTree = new QuadTree(new Boundary(1, 1, 1, 1), 1);
    }

    // Met à jour l'état global du monde (mouvements, collisions, etc.)
    public void update() {
        // Mise à jour du QuadTree et vérification des interactions
    }

    // Retourne un mapping des joueurs avec les entités qu'ils ont absorbées
    public Map<Player, List<Entity>> getAbsorptions() {
        return new HashMap<>();
    }

    // Méthodes pour ajouter un joueur ou une pastille
    public void addPlayer(Player player) {
        // Ajoute le joueur à la liste et au QuadTree
        player.setMass(1);
        Point2D pos = new Point2D(1, 1);
        player.setPosition(pos);

        players.add(player);
        quadTree.insert(player);
    }

    public void addPastille(Pellet pellet) {
        // Ajoute la pastille à la liste et au QuadTree
        pellet.setMass(1);
        Point2D pos = new Point2D(2, 1);
        pellet.setPosition(pos);

        quadTree.insert(pellet);
    }
}