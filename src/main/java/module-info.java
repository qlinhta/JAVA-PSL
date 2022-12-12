module com.example.joinfive {
    requires javafx.controls;
    requires javafx.fxml;


    opens psl.dauphine.mpsl to javafx.fxml;
    exports psl.dauphine.mpsl;
    exports psl.dauphine.mpsl.model;
    opens psl.dauphine.mpsl.model to javafx.fxml;
    exports psl.dauphine.mpsl.model.grid;
    opens psl.dauphine.mpsl.model.grid to javafx.fxml;
    exports psl.dauphine.mpsl.model.ai;
    opens psl.dauphine.mpsl.model.ai to javafx.fxml;
    exports psl.dauphine.mpsl.controller;
    opens psl.dauphine.mpsl.controller to javafx.fxml;
    exports psl.dauphine.mpsl.view;
    opens psl.dauphine.mpsl.view to javafx.fxml;
    exports psl.dauphine.mpsl.util;
    opens psl.dauphine.mpsl.util to javafx.fxml;
}