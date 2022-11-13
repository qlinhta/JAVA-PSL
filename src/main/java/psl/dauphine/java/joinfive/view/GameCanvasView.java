package psl.dauphine.java.joinfive.view;

import psl.dauphine.java.joinfive.util.GameObserver;
import psl.dauphine.java.joinfive.model.grid.Grid;
import psl.dauphine.java.joinfive.model.grid.Line;
import psl.dauphine.java.joinfive.model.grid.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class GameCanvasView implements GameObserver {

    private Canvas canvas;

    private int offX, offY;
    private int cellWidth, cellHeight;

    public GameCanvasView(Canvas canvas) {
        this.canvas = canvas;
        canvas.setWidth(700);
        canvas.setHeight(700);
    }

    public int getOffX() {
        return offX;
    }

    public int getOffY() {
        return offY;
    }

    public int getCellWidth() {
        return cellWidth;
    }

    public int getCellHeight() {
        return cellHeight;
    }

    private void calcVars(Grid grid) {
        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        int nBlocksX = grid.width() - 1;
        int nBlocksY = grid.height() - 1;

        cellWidth = width / (nBlocksX + 2);
        cellHeight = height / (nBlocksY + 2);

        offX = cellWidth;
        offY = cellHeight;
    }

    public void update(Grid grid, List<Point> highlightPoints) {
        if (cellWidth == 0 || cellHeight == 0)
            calcVars(grid);
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        g.setFill(Color.WHITE);

        g.setLineWidth(2.0);
        g.setStroke(Color.valueOf("#34495E"));
        g.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setLineWidth(1);
        g.setStroke(Color.valueOf("#AFD8F8"));
        g.setLineWidth(1);
        for (double gridX = 0; gridX < grid.width(); gridX++) {
//            g.strokeRect(0,0,width,height);
            double x1 = snap(offX + gridX * cellWidth);
            double y1 = snap(offY);
            double x2 = snap(offX + gridX * cellWidth);
            double y2 = snap(offY + (grid.height() - 1) * cellHeight);
            g.strokeLine(x1, y1, x2, y2);
//            drawLine(offX + 0, offY + gridY * cellHeight, offX + gridX * cellWidth, offY + height * cellHeight, g);
        }

        for (double gridY = 0; gridY < grid.height(); gridY++) {
            double x1 = snap(offX);
            double y1 = snap(offY + gridY * cellHeight);
            double x2 = snap(offX + (grid.width() - 1) * cellWidth);
            double y2 = snap(offY + gridY * cellHeight);
            g.strokeLine(x1, y1, x2, y2);
        }

        g.setFill(Color.valueOf("#0C3547"));
        double radius = 5;
        grid.lines().forEach(line -> drawLine(line, g));
        grid.points().forEach(point -> {
            g.fillOval(offX + cellWidth * point.x - radius, offY + cellHeight * point.y - radius, 2 * radius, 2 * radius);
        });
        grid.lines().forEach(line -> drawNumberedPoint(line.getNewPoint(), line.getNumber(), g));
        g.setFill(Color.YELLOW);
        highlightPoints.forEach(point -> {
            g.fillOval(offX + cellWidth * point.x - radius, offY + cellHeight * point.y - radius, 2 * radius, 2 * radius);
        });

        System.out.println("Drawing");
    }

    private double snap(double num) {
        return num + 0.5;
    }

    private void drawLine(Line line, GraphicsContext g) {
        g.setStroke(Color.valueOf("#0C3547"));
        g.setLineWidth(2);
        Point p1 = line.points().get(0);
        Point p2 = line.points().get(line.points().size() - 1);
        g.strokeLine(offX + cellHeight * p1.x, offY + cellHeight * p1.y, offX + cellWidth * p2.x, offY + cellHeight * p2.y);

        g.setFill(Color.valueOf("#0C3547"));
    }

    private void drawNumberedPoint(Point p, int num, GraphicsContext g) {

        double radius = 10;
        double centerX = offX + cellHeight * p.x - radius;
        double centerY = offY + cellHeight * p.y - radius;
        double numX = centerX + radius * .65 * 1.0 / (num + "").length();
        double numY = centerY + radius * 1.3;
        g.fillOval(centerX, centerY, radius * 2, radius * 2);
        g.setLineWidth(1);
        g.setStroke(Color.WHITE);
        g.strokeText(num + "", numX, numY);
    }
}
