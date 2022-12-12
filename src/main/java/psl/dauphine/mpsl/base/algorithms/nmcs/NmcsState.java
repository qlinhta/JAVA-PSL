package psl.dauphine.mpsl.base.algorithms.nmcs;

import javafx.util.Pair;
import psl.dauphine.mpsl.base.algorithms.RandomSearchAlgorithm;
import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

import java.util.LinkedList;
import java.util.List;

public class NmcsState implements INmcsState<Grid, Line> {
    private final Grid grid;

    public NmcsState(Grid grid) {
        this.grid = grid;
    }

    @Override
    public double getScore() {
        return grid.lines().size();
    }

    @Override
    public boolean isTerminalPosition() {
        return findAllLegalActions().size() == 0;
    }

    @Override
    public List<Line> findAllLegalActions() {
        List<Line> lines = grid.possibleLines();
//        System.out.println("N = " + lines);
        return lines;
    }

    @Override
    public INmcsState<Grid, Line> takeAction(Line line) {
        Grid newGrid = grid.copy();
        newGrid.addLine(line);
        return new NmcsState(newGrid);
    }

    @Override
    public Pair<Double, List<Line>> simulation() {
        List<Line> list = new LinkedList<>();
        RandomSearchAlgorithm r = new RandomSearchAlgorithm();
        while (true) {

            Line compLine = r.calcMove(grid);
            if (compLine == null) {
                break;
            }
            list.add(compLine);
            grid.addLine(compLine);
        }
        return new Pair<>(0.0 + grid.lines().size(), list);
    }
}
