package psl.dauphine.mpsl.base.algorithms.nmcs;

import javafx.util.Pair;
import psl.dauphine.mpsl.base.algorithms.JoinFiveAlgorithm;
import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class NMCSalgorithm implements JoinFiveAlgorithm {

    /**
     * Calculates the best move for the current game grid.
     *
     * @param grid the current game grid
     * @return the best move for the current game, or null if no moves are available
     */
    @Override
    public Line calcMove(Grid grid) {
        int level = 2;
        NMCSstate state = new NMCSstate(grid);
        List<Line> lines;
        final long maxRunningTimeMs = 30 * 1000;
        final long endTimeMs = System.currentTimeMillis() + maxRunningTimeMs;
        Pair<Double, List<Line>> result = _searchMS(state, level, () -> {
            System.out.println(System.currentTimeMillis() > endTimeMs);
            return System.currentTimeMillis() > endTimeMs;
        });
        lines = result.getValue();
        return lines.size() > 0 ? lines.get(0) : null;
    }

    @Override
    public String getName() {
        return "NMCS";
    }

    /**
     * Performs a Monte Carlo search to find the best move for a given game state.
     *
     * @param state      the current game state
     * @param level      the depth of the search
     * @param isCanceled a supplier that returns true if the search should be canceled
     * @param <State>    the type of the game state
     * @param <Action>   the type of the game actions
     * @return a pair containing the score for the best move and the list of actions to reach that move
     */

    public static <State, Action> Pair<Double, List<Action>> _searchMS(InterfNMCSstate<State, Action> state,
                                                                       final int level, final Supplier<Boolean> isCanceled) {
        if (level <= 0) {
            return state.simulation();
        }
        AtomicReference<Pair<Double, List<Action>>> globalBestResult;
        globalBestResult = new AtomicReference<>(new Pair<>(state.getScore(), new LinkedList<>()));
        final List<Action> visitedNodes = new LinkedList<>();
        while (!(state.isTerminalPosition() || isCanceled.get())) {
            Pair<Double, List<Action>> actualBestResult = new Pair<>(0.0, new LinkedList<>());
            Action actualBestAction = null;
            for (Action action : state.findAllLegalActions()) {
                final InterfNMCSstate<State, Action> currentState = state.takeAction(action);
                final Pair<Double, List<Action>> simulateResult = _searchMS(currentState, level - 1, isCanceled);
                if (simulateResult.getKey() >= actualBestResult.getKey()) {
                    actualBestAction = action;
                    actualBestResult = simulateResult;
                }
            }
            if (globalBestResult.get().getKey() <= actualBestResult.getKey()) {
                visitedNodes.add(actualBestAction);
                globalBestResult.set(actualBestResult);
                globalBestResult.get().getValue().addAll(0, visitedNodes);
            } else {
                actualBestAction = globalBestResult.get().getValue().get(visitedNodes.size());
                visitedNodes.add(actualBestAction);
            }
            state = state.takeAction(actualBestAction);
        }
        return globalBestResult.get();
    }
}

