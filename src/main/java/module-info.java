module fr.unicaen.iutcaen.agario2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.unicaen.iutcaen.agario2 to javafx.fxml;
    exports fr.unicaen.iutcaen.agario2;
}