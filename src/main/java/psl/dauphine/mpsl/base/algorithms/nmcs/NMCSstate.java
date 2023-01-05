package psl.dauphine.mpsl.base.algorithms.nmcs;

import javafx.util.Pair;
import psl.dauphine.mpsl.base.algorithms.RandomSearchAlgorithm;
import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

import java.util.ArrayList;
import java.util.List;

public class NMCSstate implements InterfNMCSstate<Grid, Line> {
    private final Grid grid;

    public NMCSstate(Grid grid) {
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
        return lines;
    }

    @Override
    public InterfNMCSstate<Grid, Line> takeAction(Line line) {
        Grid newGrid = grid.copy();
        newGrid.addLine(line);
        return new NMCSstate(newGrid);
    }
    /*
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
     */

    @Override
    public Pair<Double, List<Line>> simulation() {
        List<Line> list = new ArrayList<>();
        RandomSearchAlgorithm r = new RandomSearchAlgorithm();
        Line compLine = r.calcMove(grid);
        while (compLine != null) {
            list.add(compLine);
            grid.addLine(compLine);
            compLine = r.calcMove(grid);
        }
        return new Pair<>(0.0 + grid.lines().size(), list);
    }

}
