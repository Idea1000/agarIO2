package fr.unicaen.iutcaen.agario2.model;

import fr.unicaen.iutcaen.agario2.model.entities.Entity;
import fr.unicaen.iutcaen.agario2.model.entities.Pellet;

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