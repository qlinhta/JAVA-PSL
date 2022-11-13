package psl.dauphine.java.joinfive.model.ai;

import psl.dauphine.java.joinfive.model.grid.Grid;
import psl.dauphine.java.joinfive.model.grid.Line;

public interface JoinFiveAlgorithm {
    Line calcMove(Grid grid);
}
