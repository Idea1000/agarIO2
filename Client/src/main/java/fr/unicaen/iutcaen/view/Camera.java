package fr.unicaen.iutcaen.view;


import fr.unicaen.iutcaen.model.Boundary;
import fr.unicaen.iutcaen.model.Player;

public class Camera {
    private Boundary viewBoundary;
    private double zoom;

    /**
     * Initialize the camera of the game
     * @param viewBoundary
     */
    public Camera(Boundary viewBoundary) {
        this.viewBoundary = viewBoundary;
    }

    // Centre la caméra sur le joueur et ajuste le zoom en fonction de sa masse

    /**
     * Centers the camera on the player
     * @param player
     */
    public void followPlayer(Player player) {
        // Logique pour centrer la vue sur le joueur
    }

    /**
     * Sets the zoom level of the camera based on the player's mass
     * @param zoom
     */
    public void setZoom(double zoom) {
        this.zoom = zoom;
    }
}
