package com.example.joinfive;

import com.example.joinfive.controller.JoinFiveController;
import com.example.joinfive.model.GameModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinFiveApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JoinFiveApplication.class.getResource("joinfive-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 768);
        JoinFiveController controller = fxmlLoader.getController();

        GameModel gameModel = new GameModel();
        controller.setModel(gameModel);
        controller.start();
        stage.setTitle("Quyen Linh TA & Ha Anh TRAN");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}