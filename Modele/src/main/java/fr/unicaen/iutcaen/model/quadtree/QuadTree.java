package fr.unicaen.iutcaen.model.quadtree;

import fr.unicaen.iutcaen.config.Config;
import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Point;
import fr.unicaen.iutcaen.model.entities.Entity;
import fr.unicaen.iutcaen.model.entities.Pellet;
import fr.unicaen.iutcaen.model.factories.FactoryPellet;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The QuadTree is composed of four other QuadTree
 * They may be referred as "Nodes"
 * It's used to store the elements in the map
 */
public class  QuadTree {


    /**
     * the boundary covered by the Node
     */
    private Boundary boundary;

    /**
     * the entites contained in the node
     * empty if the {@link #depth} isn't the DEPTH_MAX_QT_TREE
     */
    private List<Entity> entities;

    /**
     * the depth of the current Node (0 if root)
     */
    private int depth;

    // Children Nodes (NE, NW, SE, SW)
    private QuadTree northeast;
    private QuadTree northwest;
    private QuadTree southeast;
    private QuadTree southwest;

    /**
     * creates a new Node with no children
     * @param boundary the zone covered by the Node
     * @param depth the depth of the Node
     */
    public QuadTree(Boundary boundary, int depth) {
        this.boundary = boundary;
        entities = new ArrayList<>();
        this.depth = depth;
    }

    /**
     * Inserts a Entity in the right Node
     * @param entity the entity you wish to insert
     * @return true if the entity is successfully inserted
     */
    public boolean insert(Entity entity) {
        if (depth >= Config.DEPTH_MAX_QT_TREE) {
            if (!boundary.contains(entity)) {
                return false;
            }

            entities.add(entity);
            return true;

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


    /**
     * Gives the entities in the boundary given
     * @param range the zone where the entities will come from
     * @return a list of entities found in the tree
     */
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

    /**
     * Creates the children of the current Node
     * Shouldn't be called on nodes that are at DEPTH_MAX_QT_TREE
     */
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
     * returns a list of entities that are visible by the entity passed by the parameter
     * @param entity
     * @return all the entities that are in the same node
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
     * @param entity the entity removed
     * @return true if the entity is successfully removed
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
}