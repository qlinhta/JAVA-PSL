module com.example.joinfive {
    requires javafx.controls;
    requires javafx.fxml;


    opens psl.dauphine.joinfive to javafx.fxml;
    exports psl.dauphine.joinfive;
    exports psl.dauphine.joinfive.model;
    opens psl.dauphine.joinfive.model to javafx.fxml;
    exports psl.dauphine.joinfive.model.grid;
    opens psl.dauphine.joinfive.model.grid to javafx.fxml;
    exports psl.dauphine.joinfive.model.ai;
    opens psl.dauphine.joinfive.model.ai to javafx.fxml;
    exports psl.dauphine.joinfive.controller;
    opens psl.dauphine.joinfive.controller to javafx.fxml;
    exports psl.dauphine.joinfive.view;
    opens psl.dauphine.joinfive.view to javafx.fxml;
    exports psl.dauphine.joinfive.util;
    opens psl.dauphine.joinfive.util to javafx.fxml;
}