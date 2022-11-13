package psl.dauphine.joinfive.util;

import psl.dauphine.joinfive.model.grid.Grid;
import psl.dauphine.joinfive.model.grid.Point;

import java.util.List;

public interface GameObserver {
    void update(Grid grid, List<Point> highlightPoints);
}
