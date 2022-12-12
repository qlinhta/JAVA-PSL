package psl.dauphine.mpsl.model.ai;

import psl.dauphine.mpsl.model.grid.Grid;
import psl.dauphine.mpsl.model.grid.Line;

public interface JoinFiveAlgorithm {
    Line calcMove(Grid grid);
}
