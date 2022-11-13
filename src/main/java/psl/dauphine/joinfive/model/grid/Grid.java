package psl.dauphine.joinfive.model.grid;

import java.util.*;

import static psl.dauphine.joinfive.model.grid.Direction.*;

public class Grid {
    private GridPoint[][] grid;

    public Set<Point> points() {
        return points;
    }

    private Set<Point> points;

    private List<Line> lines;

    public Grid(int width, int height) {
        this.grid = new GridPoint[width][height];
        lines = new ArrayList<>();
        points = new HashSet<>();
    }

    public void initPoint(int x, int y) {
        if (grid[x][y] == null) {
            grid[x][y] = new GridPoint(x, y);
            points.add(grid[x][y]);
        }
    }

    public List<Line> possibleLines(int x, int y) {
        if (grid[x][y] != null) {
            return new ArrayList<>();
        }
        ArrayList<Line> possibleLines = new ArrayList<>();
        for (Direction direction : values()) {
            possibleLines.addAll(possibleLines(x, y, direction));
        }
        return possibleLines;
    }


    public List<Line> possibleLines(int x, int y, Direction direction) {
        if (grid[x][y] != null) {
            return new ArrayList<>();
        }
        int dirX = 0, dirY = 0;
        switch (direction) {
            case HORIZONTAL -> dirX = 1;
            case VERTICAL -> dirY = 1;
            case RISE -> {
                dirX = 1;
                dirY = -1;
            }
            case FALL -> {
                dirX = 1;
                dirY = 1;
            }
        }
        ArrayList<Line> possibleLines = new ArrayList<>();
        for (int i = -4; i <= 0; i++) {
            Line line = new Line();
            for (int j = 0; j < 5; j++) {
                int x2 = x + dirX * (i + j);
                int y2 = y + dirY * (i + j);
                if (!isValidX(x2) || !isValidY(y2)) {
                    line = null;
                    break;
                }
                if (grid[x2][y2] != null && !grid[x2][y2].isLocked(direction)) {
                    line.addPoint(new Point(x2, y2));
                } else if (x2 == x && y2 == y) {
                    Point p = new Point(x2, y2);
                    line.addPoint(p);
                    line.setNewPoint(p);
                } else {
                    line = null;
                    break;
                }
            }
            if (line != null) {
                line.setDirection(direction);
                possibleLines.add(line);
            }
            System.out.println();
        }
        return possibleLines;
    }

    public boolean isValidX(int x) {
        return x >= 0 && x < width();
    }

    public boolean isValidY(int y) {
        return y >= 0 && y < height();
    }

    public int width() {
        return grid.length;
    }

    public int height() {
        return grid[0].length;
    }

    public void init() {
        int startX = (width() - 10) / 2;
        int startY = (height() - 10) / 2;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (i < 3 && (j < 3 || j > 6)) continue;
                if (i > 6 && (j < 3 || j > 6)) continue;
                if (i > 0 && i < 9 && j > 3 && j < 6) continue;
                if (j > 0 && j < 9 && i > 3 && i < 6) continue;
                initPoint(startX + i, startY + j);
            }
        }
        System.out.println(lines);
    }

    public void addLine(List<Point> points, Direction direction) {
        Line line = new Line(points, direction);
        for (Point point : points) {
            if (grid[point.x][point.y] == null) {
                initPoint(point.x, point.y);
                line.setNewPoint(grid[point.x][point.y]);
                break;
            }
        }
        lines.add(line);
    }

    public void addLine(Line line) {
        line.setNumber(lines.size() + 1);
        for (Point point : line.points()) {
            System.out.println("CHECKING " + point);
            if (grid[point.x][point.y] == null) {
                System.out.println("FOUND " + point);
                initPoint(point.x, point.y);
                line.setNewPoint(grid[point.x][point.y]);
            }
            grid[point.x][point.y].lock(line.getDirection());
        }
        lines.add(line);
    }

    public void deletePoint(int x, int y) {
        grid[x][y] = null;
    }
    public Collection<Point> getNeighbors(Point point) {
        List<Point> neighbors = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                int x = point.x + i;
                int y = point.y + j;
                if (isValidX(x) && isValidY(y)) {
                    neighbors.add(new Point(x, y));
                }
            }
        }
        return neighbors;
    }

    public List<Line> lines() {
        return lines;
    }
}
