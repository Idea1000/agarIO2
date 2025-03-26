package fr.unicaen.iutcaen.ai;

import fr.unicaen.iutcaen.model.entities.Entity;

public interface AIBehavior {
    void update(IA ia, Entity target);
}

