package psl.dauphine.mpsl.base.algorithms.nmcs;

import javafx.util.Pair;

import java.util.List;

public interface InterfNMCSstate<State, Action> {
    double getScore();

    boolean isTerminalPosition();

    List<Action> findAllLegalActions();

    InterfNMCSstate<State, Action> takeAction(Action action);

    Pair<Double, List<Action>> simulation();
}