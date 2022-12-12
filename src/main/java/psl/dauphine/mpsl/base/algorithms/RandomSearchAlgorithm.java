package psl.dauphine.mpsl.base.algorithms;

import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

import java.util.List;

/**
 * Picks a random move from all the possible moves
 */
public class RandomSearchAlgorithm implements JoinFiveAlgorithm {
    @Override
    public Line calcMove(Grid grid) {
        List<Line> possibleLines = grid.possibleLines();
        if (possibleLines.isEmpty()) return null;
        else return possibleLines.get((int) (Math.random() * possibleLines.size()));
    }

    @Override
    public String getName() {
        return "Random Search";
    }
}
