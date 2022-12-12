package psl.dauphine.mpsl.base.grid;

public class Point {

    public final int x, y;

    private int hash;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return String.format("(%d,%d)", x, y);
    }


    public boolean equals(Object other) {
        if (other.getClass() != getClass())
            return false;
        Point p = (Point) other;
        return x == p.x && y == p.y;
    }

    @Override
    public int hashCode() {
        if (hash == 0) {
            long bits = 7L;
            bits = 31L * bits + x;
            bits = 31L * bits + y;
            hash = (int) (bits ^ (bits >> 32));
        }
        return hash;
    }
}
