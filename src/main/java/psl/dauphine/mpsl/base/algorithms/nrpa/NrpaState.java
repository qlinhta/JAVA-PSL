package psl.dauphine.mpsl.base.algorithms.nrpa;

import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

import java.util.List;

public class NrpaState implements INrpaState<Grid, Line>{

    private final Grid grid;

    public NrpaState(Grid grid) {
        this.grid = grid;
    }

    @Override
    public double getScore() {
        return grid.lines().size();
    }

    @Override
    public List<Line> findAllLegalActions() {
        return grid.possibleLines();
    }

    @Override
    public INrpaState<Grid, Line> takeAction(Line line) {
        Grid newGrid = grid.copy();
        newGrid.addLine(line);
        return new NrpaState(newGrid);
    }


    @Override
    public boolean isTerminalPosition() {
        return findAllLegalActions().size() == 0;
    }

    @Override
    public INrpaState<Grid, Line> copy() {
        return new NrpaState(grid.copy());
    }
}
