module psl.dauphine.mpsl {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;
    requires java.logging;
    requires org.jetbrains.annotations;


    opens psl.dauphine.mpsl to javafx.fxml;
    exports psl.dauphine.mpsl;
    exports psl.dauphine.mpsl.base;
    opens psl.dauphine.mpsl.base to javafx.fxml;
    exports psl.dauphine.mpsl.base.grid;
    opens psl.dauphine.mpsl.base.grid to javafx.fxml;
    exports psl.dauphine.mpsl.base.algorithms;
    opens psl.dauphine.mpsl.base.algorithms to javafx.fxml;
    exports psl.dauphine.mpsl.controller;
    opens psl.dauphine.mpsl.controller to javafx.fxml;
    exports psl.dauphine.mpsl.view;
    opens psl.dauphine.mpsl.view to javafx.fxml;
    exports psl.dauphine.mpsl.util;
    opens psl.dauphine.mpsl.util to javafx.fxml;
    exports psl.dauphine.mpsl.base.algorithms.nmcs;
    opens psl.dauphine.mpsl.base.algorithms.nmcs to javafx.fxml;
}