module fr.unicaen.iutcaen.agario2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.unicaen.iutcaen.agario2.view to javafx.fxml;
    exports fr.unicaen.iutcaen.agario2.view;
}