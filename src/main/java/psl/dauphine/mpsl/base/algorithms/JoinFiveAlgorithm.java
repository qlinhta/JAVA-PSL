package psl.dauphine.mpsl.base.algorithms;

import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

public interface JoinFiveAlgorithm {
    /**
     * Calculates a game move- A move is simply a line that is drawn on the given grid.
     *
     * @param grid the grid
     * @return
     */
    Line calcMove(Grid grid);

    String getName();
}
