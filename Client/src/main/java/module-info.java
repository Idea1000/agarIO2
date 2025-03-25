module fr.unicaen.iutcaen.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires agarIO2Modele;  // Ensure this matches the module name in agarIO2Modele's module-info

    opens fr.unicaen.iutcaen.view to javafx.fxml;
    exports fr.unicaen.iutcaen.view;


    opens fr.unicaen.iutcaen.launcher to javafx.fxml;
    exports fr.unicaen.iutcaen.launcher;
}

