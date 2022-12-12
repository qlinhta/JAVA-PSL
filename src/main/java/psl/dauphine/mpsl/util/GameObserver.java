package psl.dauphine.mpsl.util;

import java.util.List;
import psl.dauphine.mpsl.model.grid.Grid;
import psl.dauphine.mpsl.model.grid.Point;

public interface GameObserver {
    void update(Grid grid, List<Point> highlightPoints);
}
