module fr.unicaen.iutcaen.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires agarIO2Modele;  // Ensure this matches the module name in agarIO2Modele's module-info

    opens fr.unicaen.iutcaen.view to javafx.fxml;
    exports fr.unicaen.iutcaen.view;

<<<<<<< HEAD
    /*opens fr.unicaen.iutcaen.agario2.view to javafx.fxml;
   exports fr.unicaen.iutcaen.agario2.view;

    opens fr.unicaen.iutcaen.agario2.launcher to javafx.fxml;
    exports fr.unicaen.iutcaen.agario2.launcher;*/
}
=======
    opens fr.unicaen.iutcaen.launcher to javafx.fxml;
    exports fr.unicaen.iutcaen.launcher;
}
>>>>>>> archi
