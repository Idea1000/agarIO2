package fr.unicaen.iutcaen.model;

import fr.unicaen.iutcaen.model.entities.Entity;

import java.util.List;

public class QuadTree {
    private Boundary boundary;
    private int capacity; // nombre maximum d'entités par zone
    private List<Entity> entities;
    private boolean divided;

    // Sous-quadtrees (NE, NW, SE, SW)
    private QuadTree northeast;
    private QuadTree northwest;
    private QuadTree southeast;
    private QuadTree southwest;

    public QuadTree(Boundary boundary, int capacity) {
        // Initialisation du QuadTree avec sa zone et sa capacité
    }

    // Insère une entité dans le QuadTree
    public void insert(Entity entity) {
        // Logique d'insertion
    }

    // Retourne la liste des entités dans une zone donnée
    public List<Entity> query(Boundary range) {
        return null;
    }

    // Divise la zone en quatre sous-zones
    public void subdivide() {
        // Création des 4 quadtrees enfants
    }
}