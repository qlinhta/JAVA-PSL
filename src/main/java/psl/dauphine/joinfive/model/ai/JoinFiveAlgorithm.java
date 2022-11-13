package psl.dauphine.joinfive.model.ai;

import psl.dauphine.joinfive.model.grid.Grid;
import psl.dauphine.joinfive.model.grid.Line;

public interface JoinFiveAlgorithm {
    Line calcMove(Grid grid);
}
