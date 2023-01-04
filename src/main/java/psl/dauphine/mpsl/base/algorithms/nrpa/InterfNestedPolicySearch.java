package psl.dauphine.mpsl.base.algorithms.nrpa;

import java.util.List;

public interface InterfNestedPolicySearch<TState, TAction> {
    double getScore();

    List<TAction> findAllLegalActions();

    InterfNestedPolicySearch<TState, TAction> takeAction(TAction action);

//    Pair<Double, List<TAction>> simulation(NestedPolicySetup policy);

    boolean isTerminalPosition();

    InterfNestedPolicySearch<TState, TAction> copy();
}
