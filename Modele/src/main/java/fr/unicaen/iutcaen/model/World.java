package fr.unicaen.iutcaen.model;

import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.quadtree.QuadTree;
import javafx.geometry.Point2D;

import java.util.*;

import static fr.unicaen.iutcaen.Config.BASESPEED;
import static fr.unicaen.iutcaen.Config.QT_NODE_CAPACITY;

public class World {
    private List<Player> players;
    private List<Pellet> pellets;
    private QuadTree quadTree;

    public World() {
        // Initialisation des listes et du QuadTree selon la taille de la carte

        players = new ArrayList<Player>();
        pellets = new ArrayList<Pellet>();
        quadTree = new QuadTree(new Boundary(1, 1, 1, 1), QT_NODE_CAPACITY);
    }

    // Met à jour l'état global du monde (mouvements, collisions, etc.)
    public void update() {
        // Mise à jour du QuadTree et vérification des interactions
        HashMap<Player, List<Entity>> up;

    }

    // Retourne un mapping des joueurs avec les entités qu'ils ont absorbées
    public Map<Player, List<Entity>> getAbsorptions() {
        HashMap res = new HashMap<>();
        for (Player player: players) {
            res.put(player, null);
        }
        return res;
    }

    // Méthodes pour ajouter un joueur ou une pastille
    public void addPlayer(Player player) {
        // Ajoute le joueur à la liste et au QuadTree

        players.add(player);
        quadTree.insert(player.getCells());
    }

    public void addPellet(Pellet pellet) {
        // Ajoute la pastille à la liste et au QuadTree
        pellet.setMass(1);

        Random xpos = new Random();
        Random ypos = new Random();

        Point pos = new Point(xpos.nextDouble(0, 6), ypos.nextDouble(0, 6));
        pellet.setPosition(pos);

        quadTree.insert(pellet);
    }
}