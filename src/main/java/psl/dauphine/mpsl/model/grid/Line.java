package psl.dauphine.mpsl.model.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Line {
    private List<Point> points;

    private Point newPoint;

    private int number;
    private Direction direction;

    public Line() {
        points = new ArrayList<>();
    }

    public Line(Collection<Point> points, Direction direction) {
        this.points = new ArrayList<>();
        this.points.addAll(points);
        this.direction = direction;
    }

    public Line(Collection<Point> points, Point newPoint, Direction direction) {
        this.points = new ArrayList<>();
        this.points.addAll(points);
        this.newPoint = newPoint;
        this.direction = direction;
    }

    public Line(Collection<Point> points) {
        this.points = new ArrayList<>();
        this.points.addAll(points);
    }

    public void addPoint(Point point) {
        points.add(point);
    }

    @Override
	public String toString() {
        return newPoint + "-" + direction + ": " + points.toString();
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Point> points() {
        return points;
    }

    public void setNewPoint(Point newPoint) {
        this.newPoint = newPoint;
    }

    public Point getNewPoint() {
        return newPoint;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
