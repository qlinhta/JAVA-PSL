package psl.dauphine.mpsl.base.algorithms.nrpa;

import java.util.List;

public interface INrpaState<TState, TAction> {
    double getScore();

    List<TAction> findAllLegalActions();

    INrpaState<TState, TAction> takeAction(TAction action);

//    Pair<Double, List<TAction>> simulation(NrpaPolicy policy);

    boolean isTerminalPosition();

    INrpaState<TState, TAction> copy();
}
