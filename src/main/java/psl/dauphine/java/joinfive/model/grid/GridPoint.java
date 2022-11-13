package psl.dauphine.java.joinfive.model.grid;

import java.util.HashSet;
import java.util.Set;

public class GridPoint extends Point {
    private final Set<Direction> lockedDirections;


    public boolean isLocked(Direction direction) {
        return lockedDirections.contains(direction);
    }

    public void lock(Direction direction) {
        lockedDirections.add(direction);
    }


    public GridPoint(int x, int y) {
        super(x, y);
        lockedDirections = new HashSet<>();
    }
}
