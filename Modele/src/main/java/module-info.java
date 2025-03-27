module agarIO2Modele {
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    exports fr.unicaen.iutcaen.model;
    exports fr.unicaen.iutcaen.model.entities;
    exports fr.unicaen.iutcaen.model.factories;
    exports fr.unicaen.iutcaen.ai;
    opens fr.unicaen.iutcaen.viewTest to javafx.fxml;
    exports fr.unicaen.iutcaen.viewTest;
    exports fr.unicaen.iutcaen.model.quadtree;
    exports fr.unicaen.iutcaen.networkProtocol;
}