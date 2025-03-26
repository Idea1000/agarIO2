package fr.unicaen.iutcaen.model;

import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.quadtree.QuadTree;
//import fr.unicaen.iutcaen.model.quadtree.QuadTree;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class World {
    private List<Player> players;
    private List<Pellet> pellets;
    private QuadTree quadTree;

    public World() {
        // Initialisation des listes et du QuadTree selon la taille de la carte
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
    }

    public void addPastille(Pellet pellet) {
        // Ajoute la pastille à la liste et au QuadTree
    }
}