package psl.dauphine.mpsl.base.algorithms.nmcs;

import javafx.util.Pair;
import psl.dauphine.mpsl.base.algorithms.JoinFiveAlgorithm;
import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
public class NmcsAlgorithm implements JoinFiveAlgorithm {
    @Override
    public Line calcMove(Grid grid) {

        int level = 2;
        NmcsState state = new NmcsState(grid);
        List<Line> lines;
        final long maxRunningTimeMs = 2 * 1000;
        final long endTimeMs = System.currentTimeMillis() + maxRunningTimeMs;
        Pair<Double, List<Line>> result = executeSearch(state, level, () -> {

            System.out.println(System.currentTimeMillis() > endTimeMs);
            return System.currentTimeMillis() > endTimeMs;
        });
        //System.out.println("NMCS" + result.getValue().size());
        lines = result.getValue();

//        return lines.get(current++);
        return lines.size() > 0 ? lines.get(0) : null;
    }

    @Override
    public String getName() {
        return "NMCS";
    }

    public static <TState, TAction> Pair<Double, List<TAction>> executeSearch(INmcsState<TState, TAction> state,
                                                                              final int level, final Supplier<Boolean> isCanceled) {

        // terminating case
        if (level <= 0)
            return state.simulation();

        Pair<Double, List<TAction>> globalBestResult = new Pair<>(state.getScore(), new LinkedList<>());
        final List<TAction> visitedNodes = new LinkedList<>();
        //System.out.println("Level :" + level + " terminal = " + state.isTerminalPosition());
        while (!state.isTerminalPosition() && !isCanceled.get()) {

            Pair<Double, List<TAction>> currentBestResult = new Pair<>(0.0, new LinkedList<TAction>());
            TAction currentBestAction = null;
            ////System.out.println("While loop");
            for (TAction action : state.findAllLegalActions()) {
                //System.out.println("FOr loop");
                final INmcsState<TState, TAction> currentState = state.takeAction(action);
                // recursion
                final Pair<Double, List<TAction>> simulationResult = executeSearch(currentState, level - 1, isCanceled);

                if (simulationResult.getKey() >= currentBestResult.getKey()) {
                    currentBestAction = action;
                    currentBestResult = simulationResult;
                }
            }

            if (currentBestResult.getKey() >= globalBestResult.getKey()) {
                visitedNodes.add(currentBestAction);
                globalBestResult = currentBestResult;
                globalBestResult.getValue().addAll(0, visitedNodes);
            } else {
                currentBestAction = globalBestResult.getValue().get(visitedNodes.size());
                visitedNodes.add(currentBestAction);
            }

            state = state.takeAction(currentBestAction);
        }
        return globalBestResult;
    }
}
