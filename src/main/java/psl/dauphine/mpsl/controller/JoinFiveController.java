package psl.dauphine.mpsl.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import psl.dauphine.mpsl.model.GameModel;
import psl.dauphine.mpsl.model.ai.AlgorithmFactory;
import psl.dauphine.mpsl.view.GameCanvasView;
import java.util.Objects;

public class JoinFiveController {
    @FXML
    private TextField usernameField;

    @FXML
    private ComboBox<String> playerOptions;

    @FXML
    private ComboBox<String> algorithmOptions;

    @FXML
    private ComboBox<String> gameModeOptions;

    @FXML
    private Canvas gameCanvas;
    //    private Grid grid;
    private GameCanvasView canvasView;
    private GameModel gameModel;

    @FXML
    private void displayHistory() {
        System.out.println("History");
    }

    @FXML
    private void displayBestGrid() {
        System.out.println("Best Grid");

    }

    @FXML
    private void canvasMousePressed(MouseEvent me) {
        System.out.printf("(%f,%f)", me.getX(), me.getY());
        double tolerance = 0.2;
        double tempX = (me.getX() - canvasView.getOffX()) / canvasView.getCellWidth();
        double tempY = (me.getY() - canvasView.getOffY()) / canvasView.getCellHeight();
        double frX = tempX - (int) tempX;
        double frY = tempY - (int) tempY;
        if ((frX > tolerance && frX < 1 - tolerance) || (frY > tolerance && frY < 1 - tolerance)) {
            System.out.println("INVALID");
            return;
        }
        int gridX = (int) Math.round(tempX);
        int gridY = (int) Math.round(tempY);
        gameModel.handleHumanMove(gridX, gridY);
//                System.out.printf("(%d,%d)\n", gridX, gridY);
    }

    public void start() {
        canvasView = new GameCanvasView(gameCanvas);
        gameCanvas.setFocusTraversable(true);
        gameModel.addObserver(canvasView);
        setupOptions();
        gameModel.init();
    }

    private void setupOptions() {
        playerOptions.getItems().removeAll(playerOptions.getItems());
        playerOptions.getItems().addAll("Human", "Computer");
        playerOptions.getSelectionModel().select("Human");

        algorithmOptions.getItems().removeAll(algorithmOptions.getItems());
        algorithmOptions.getItems().addAll(AlgorithmFactory.availableAlgorithmNames());
        algorithmOptions.getSelectionModel().select(0);
    }

    @FXML
    private void playerChanged() {
        if (playerOptions.getSelectionModel().getSelectedItem().equals("Computer")) {
            String algorithmName = algorithmOptions.getSelectionModel().getSelectedItem().trim();
            gameModel.computerPlay(Objects.requireNonNull(AlgorithmFactory.createAlgorithm(algorithmName)));
        }
    }

    @FXML
    private void gameModeChanged() {
        gameModel.setGameMode(gameModeOptions.getSelectionModel().getSelectedItem().trim());
    }

    public void setModel(GameModel gameModel) {
        this.gameModel = gameModel;
    }
}