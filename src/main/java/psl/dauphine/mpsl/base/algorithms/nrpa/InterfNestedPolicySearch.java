package psl.dauphine.mpsl.base.algorithms.nrpa;

import java.util.List;

public interface InterfNestedPolicySearch<State, Action> {
    double getScore();

    List<Action> findAllLegalActions();

    InterfNestedPolicySearch<State, Action> takeAction(Action action);

    boolean isTerminalPosition();

    InterfNestedPolicySearch<State, Action> copy();
}
