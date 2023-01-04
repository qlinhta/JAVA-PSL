package psl.dauphine.mpsl.base.algorithms.nrpa;

import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

import java.util.List;

public class NestedPolicyState implements InterfNestedPolicySearch<Grid, Line> {

    private final Grid grid;

    public NestedPolicyState(Grid grid) {
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
    public InterfNestedPolicySearch<Grid, Line> takeAction(Line line) {
        Grid newGrid = grid.copy();
        newGrid.addLine(line);
        return new NestedPolicyState(newGrid);
    }


    @Override
    public boolean isTerminalPosition() {
        return findAllLegalActions().size() == 0;
    }

    @Override
    public InterfNestedPolicySearch<Grid, Line> copy() {
        return new NestedPolicyState(grid.copy());
    }
}
