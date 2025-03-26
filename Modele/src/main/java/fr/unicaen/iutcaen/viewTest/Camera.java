package fr.unicaen.iutcaen.viewTest;

import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;

public class Camera {
    private Boundary viewBoundary;
    private double zoom;

    public Camera(Boundary viewBoundary) {
        this.viewBoundary = viewBoundary;
    }

    // Centre la caméra sur le joueur et ajuste le zoom en fonction de sa masse
    public void followPlayer(Player player) {
        // Logique pour centrer la vue sur le joueur
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
