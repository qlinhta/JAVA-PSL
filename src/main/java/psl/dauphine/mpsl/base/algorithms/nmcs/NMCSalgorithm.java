package psl.dauphine.mpsl.base.algorithms.nmcs;

import javafx.util.Pair;
import psl.dauphine.mpsl.base.algorithms.JoinFiveAlgorithm;
import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class NMCSalgorithm implements JoinFiveAlgorithm {
    @Override
    public Line calcMove(Grid grid) {
        int level = 5;
        NMCSstate state = new NMCSstate(grid);
        List<Line> lines;
        final long maxRunningTimeMs = 30 * 1000;
        final long endTimeMs = System.currentTimeMillis() + maxRunningTimeMs;
        Pair<Double, List<Line>> result = _searchMS(state, level, () -> {
            System.out.println(endTimeMs < System.currentTimeMillis());
            return System.currentTimeMillis() > endTimeMs;
        });
        lines = result.getValue();
        return lines.size() > 0 ? lines.get(0) : null;
    }

    @Override
    public String getName() {
        return "NMCS";
    }

    public static <State, Action> Pair<Double, List<Action>> _searchMS(InterfNMCSstate<State, Action> state,
                                                                       final int level, final Supplier<Boolean> isCanceled) {
        if (level <= 0)
            return state.simulation();

        Pair<Double, List<Action>> globalBestResult = new Pair<>(state.getScore(), new LinkedList<>());
        final List<Action> visitedNodes = new LinkedList<>();
        while (!state.isTerminalPosition() && !isCanceled.get()) {

            Pair<Double, List<Action>> actualBestResult = new Pair<>(0.0, new LinkedList<Action>());
            Action actualBestAction = null;
            for (Action action : state.findAllLegalActions()) {
                final InterfNMCSstate<State, Action> currentState = state.takeAction(action);
                final Pair<Double, List<Action>> simulateResult = _searchMS(currentState, level - 1, isCanceled);
                if (simulateResult.getKey() >= actualBestResult.getKey()) {
                    actualBestAction = action;
                    actualBestResult = simulateResult;
                }
            }
            if (actualBestResult.getKey() >= globalBestResult.getKey()) {
                visitedNodes.add(actualBestAction);
                globalBestResult = actualBestResult;
                globalBestResult.getValue().addAll(0, visitedNodes);
            } else {
                actualBestAction = globalBestResult.getValue().get(visitedNodes.size());
                visitedNodes.add(actualBestAction);
            }
            state = state.takeAction(actualBestAction);
        }
        return globalBestResult;
    }
}
