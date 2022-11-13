package psl.dauphine.java.joinfive.model;

import psl.dauphine.java.joinfive.util.GameObserver;
import psl.dauphine.java.joinfive.model.ai.JoinFiveAlgorithm;
import psl.dauphine.java.joinfive.model.grid.Grid;
import psl.dauphine.java.joinfive.model.grid.Line;
import psl.dauphine.java.joinfive.model.grid.Point;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private final Grid grid;
    private boolean selectingLine;

    private List<Line> possibleLines;
    private final ArrayList<GameObserver> gameObservers;

    public GameModel() {
        grid = new Grid(20, 20); // to be changed to infinite grid
        grid.init();
        gameObservers = new ArrayList<>();
        possibleLines = new ArrayList<>();
    }

    private void updateGameObservers() {
        List<Point> points = new ArrayList<>();
        for (Line line : possibleLines) {
            List<Point> otherPoints = new ArrayList<>();
            possibleLines.stream().filter(l -> l != line).toList()
                    .forEach(l -> otherPoints.addAll(l.points()));
            for (Point point : line.points()) {
                if (!otherPoints.contains(point)) {
                    points.add(point);
                    break;
                }
            }
        }
        gameObservers.forEach(gameObserver -> gameObserver.update(grid, points));
    }

    public void addObserver(GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    public void init() {
        grid.init();
        updateGameObservers();
    }

    public void computerPlay(JoinFiveAlgorithm algorithm) {
        while (true) {
            Line compLine = algorithm.calcMove(grid);
            if (compLine == null) {
                gameOver();
                break;
            }
            makeMove(compLine);
        }
    }

    private void gameOver() {
        System.out.println("GAME OVER !");
    }

    public void handleHumanMove(int x, int y) {
        if (selectingLine) {
            for (Line line : possibleLines) {
                for (Point point : line.points()) {
                    if (point.x == x && point.y == y) {
                        makeMove(line);
                        selectingLine = false;
                        return;
                    }
                }
            }
            System.out.println("Invalid Move");
            return;
        }
        List<Line> possibleLines = grid.possibleLines(x, y);
        if (possibleLines.size() == 0) {
            System.out.println("Invalid Move");
        } else if (possibleLines.size() == 1) {
            makeMove(possibleLines.get(0));
        } else {
            selectingLine = true;
            System.out.println("More than one possible");
            this.possibleLines = possibleLines;
            updateGameObservers();
        }
        System.out.println(possibleLines);
//        possibleLines.forEach(grid::addLine);
    }

    private void makeMove(Line line) {
        grid.addLine(line);
        possibleLines.clear();
        updateGameObservers();
    }

    public void setGameMode(String gameMode) {

    }
}
