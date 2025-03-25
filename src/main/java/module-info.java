module fr.unicaen.iutcaen.agario3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens fr.unicaen.iutcaen.agario3 to javafx.fxml;
    exports fr.unicaen.iutcaen.agario3;
}