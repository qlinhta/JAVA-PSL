package psl.dauphine.mpsl.base.algorithms.nmcs;

import javafx.util.Pair;

import java.util.List;

public interface INmcsState<TState, TAction> {
    double getScore();
    boolean isTerminalPosition(); // for convenience (same as findAllLegalActions().size() == 0)
    List<TAction> findAllLegalActions();
    INmcsState<TState, TAction> takeAction(TAction action);
    Pair<Double, List<TAction>> simulation();
}