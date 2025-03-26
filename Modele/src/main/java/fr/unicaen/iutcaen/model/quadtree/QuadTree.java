package fr.unicaen.iutcaen.model.quadtree;

import fr.unicaen.iutcaen.Config;
import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryPellet;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class  QuadTree {
    private Boundary boundary;
    private List<Entity> entities;

    private int depth;

    // Sous-quadtrees (NE, NW, SE, SW)
    private QuadTree northeast;
    private QuadTree northwest;
    private QuadTree southeast;
    private QuadTree southwest;

    public QuadTree(Boundary boundary, int depth) {
        this.boundary = boundary;
        entities = new ArrayList<>();
        this.depth = depth;
    }

    // Insère une entité dans le QuadTree
    public boolean insert(Entity entity) {
        if (depth >= Config.DEPTH_MAX_QT_TREE) {
            if (!boundary.contains(entity)) {
                return false;
            }

            if (entities.size() < Config.QT_NODE_CAPACITY) {
                entities.add(entity);
                return true;
            }
        }
        if (northwest == null){
            subdivide();
        }

        if (northwest.insert(entity)) return true;
        if (northeast.insert(entity)) return true;
        if (southwest.insert(entity)) return true;
        if (southeast.insert(entity)) return true;

        return false;
    }


    // Retourne la liste des entités dans une zone donnée
    public List<Entity> query(Boundary range) {
        ArrayList<Entity> entitiesInRange = new ArrayList<>();

        if (!boundary.intersects(range)){
            return entitiesInRange;
        }

        for (int i = 0; i < entities.size(); i++) {
            if (range.contains(entities.get(i))){
                entitiesInRange.add(entities.get(i));
            }
        }

        if (northwest == null){
            return entitiesInRange;
        }

        entitiesInRange.addAll(northwest.query(range));
        entitiesInRange.addAll(northeast.query(range));
        entitiesInRange.addAll(southwest.query(range));
        entitiesInRange.addAll(southeast.query(range));

        return entitiesInRange;
    }

    // Divise la zone en quatre sous-zones
    public void subdivide() {
        double x = boundary.getX();
        double y = boundary.getY();
        double w = boundary.getWidth();
        double h = boundary.getHeight();

        northwest = new QuadTree(new Boundary(x, y, w / 2.0, h / 2.0), depth+1);
        northeast = new QuadTree(new Boundary(w / 2.0 + x , y, w / 2.0, h / 2.0),depth+1);
        southeast = new QuadTree(new Boundary(w / 2.0 + x , h / 2 + y, w / 2.0, h / 2.0),depth+1);
        southwest = new QuadTree(new Boundary(x , h / 2 + y, w / 2.0, h / 2.0),depth+1);
    }

    /**
     * returns a list of entities that are visible by the entity passsed by the parameter
     * @param entity
     * @return
     */
    public List<Entity> getEntitiesAround(Entity entity){

        if (depth >= Config.DEPTH_MAX_QT_TREE) {
            if (!boundary.contains(entity)) {
                return null;
            }

            return entities;
        }

        if (northwest == null){
            return null;
        }

        List<Entity> rep;
        if ( (rep = northwest.getEntitiesAround(entity)) != null ) return rep;
        if ( (rep = northeast.getEntitiesAround(entity)) != null) return rep;
        if ( (rep = southwest.getEntitiesAround(entity)) != null ) return rep;
        if ((rep = southeast.getEntitiesAround(entity)) != null ) return rep;

        return null;
    }

    /**
     * removes the entity from world
     * @param entity
     * @return
     */
    public boolean removeEntity(Entity entity) {

        if (depth >= Config.DEPTH_MAX_QT_TREE) {
            if (!boundary.contains(entity)) {
                return false;
            }

            return entities.remove(entity);
        }

        if (northwest == null){
            return false;
        }

        if (northwest.removeEntity(entity)) return true;
        if (northeast.removeEntity(entity)) return true;
        if (southwest.removeEntity(entity)) return true;
        if (southeast.removeEntity(entity)) return true;

        return false;
    }

    public boolean contains(Entity entity){
        if (depth >= Config.DEPTH_MAX_QT_TREE) {
            if (!boundary.contains(entity)) {
                return false;
            }

            return true;
        }

        if (northwest == null){
            return false;
        }

        if (northwest.contains(entity)) return true;
        if (northeast.contains(entity)) return true;
        if (southwest.contains(entity)) return true;
        if (southeast.contains(entity)) return true;

        return false;
    }

//    public static void main(String[] args) {
//        QuadTree q = new QuadTree(new Boundary(0, 0, 128, 128),0);
//        FactoryPellet f = new FactoryPellet();
//        q.insert(f.fabrique(new Point(5.0, 12.0), 2, Color.ALICEBLUE));
//        q.insert(f.fabrique(new Point(6.0, 12.0), 2, Color.ALICEBLUE));
//        q.insert(f.fabrique(new Point(7.0, 12.0), 2, Color.ALICEBLUE));
//        q.insert(f.fabrique(new Point(8.0, 12.0), 2, Color.ALICEBLUE));
//        q.insert(f.fabrique(new Point(9.0, 12.0), 2, Color.ALICEBLUE));
//        q.insert(f.fabrique(new Point(10.0, 12.0), 2, Color.ALICEBLUE));
//        q.insert(f.fabrique(new Point(11.0, 12.0), 2, Color.ALICEBLUE));
//        q.insert(f.fabrique(new Point(12.0, 12.0), 2, Color.ALICEBLUE));
//
//        System.out.println(q.query(new Boundary(4.0, 0.0, 10.0, 60.0)).size());
//        System.out.println(q.entities.size());
//    }
}