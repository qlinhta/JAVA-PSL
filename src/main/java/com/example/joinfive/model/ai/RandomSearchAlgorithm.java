package com.example.joinfive.model.ai;

import com.example.joinfive.model.grid.Grid;
import com.example.joinfive.model.grid.Line;
import com.example.joinfive.model.grid.Point;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RandomSearchAlgorithm implements JoinFiveAlgorithm {
    @Override
    public Line calcMove(Grid grid) {
        HashSet<Point> pointsSoFar = new HashSet<>();
        List<Line> possibleLines = new ArrayList<>();
        grid.points().forEach(gridPoint -> grid.getNeighbors(gridPoint).stream()
                .filter(point -> !pointsSoFar.contains(gridPoint))
                .forEach(point -> {
                    pointsSoFar.add(point);
                    possibleLines.addAll(grid.possibleLines(point.x, point.y));
                }));
        if (possibleLines.isEmpty()) return null;
        else return possibleLines.get((int) (Math.random() * possibleLines.size()));
    }
}
