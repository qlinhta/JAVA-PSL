package psl.dauphine.java.joinfive.util;

import psl.dauphine.java.joinfive.model.grid.Grid;
import psl.dauphine.java.joinfive.model.grid.Point;

import java.util.List;

public interface GameObserver {
    void update(Grid grid, List<Point> highlightPoints);
}
