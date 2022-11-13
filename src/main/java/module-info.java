module com.example.joinfive {
    requires javafx.controls;
    requires javafx.fxml;


    opens psl.dauphine.java.joinfive to javafx.fxml;
    exports psl.dauphine.java.joinfive;
    exports psl.dauphine.java.joinfive.model;
    opens psl.dauphine.java.joinfive.model to javafx.fxml;
    exports psl.dauphine.java.joinfive.model.grid;
    opens psl.dauphine.java.joinfive.model.grid to javafx.fxml;
    exports psl.dauphine.java.joinfive.model.ai;
    opens psl.dauphine.java.joinfive.model.ai to javafx.fxml;
    exports psl.dauphine.java.joinfive.controller;
    opens psl.dauphine.java.joinfive.controller to javafx.fxml;
    exports psl.dauphine.java.joinfive.view;
    opens psl.dauphine.java.joinfive.view to javafx.fxml;
    exports psl.dauphine.java.joinfive.util;
    opens psl.dauphine.java.joinfive.util to javafx.fxml;
}