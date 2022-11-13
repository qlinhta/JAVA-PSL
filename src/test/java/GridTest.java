import psl.dauphine.joinfive.model.grid.Direction;
import psl.dauphine.joinfive.model.grid.Grid;
import psl.dauphine.joinfive.model.grid.Line;
import org.junit.jupiter.api.Test;

import java.util.List;

public class GridTest {

    @Test
    public void testPossibleLines() {
        Grid grid = new Grid(10, 10);
        for (int x = 0; x < grid.width(); x++) {
            for (int y = 0; y < grid.height(); y++) {
                grid.initPoint(x, y);
            }
        }
        grid.deletePoint(5, 5);
        for (Direction direction : Direction.values()) {
            List<Line> lines = grid.possibleLines(5, 5, direction);
            System.out.println(direction + "\n" + lines);
        }


    }
}
