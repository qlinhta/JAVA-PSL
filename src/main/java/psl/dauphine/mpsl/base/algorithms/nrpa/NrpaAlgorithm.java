package psl.dauphine.mpsl.base.algorithms.nrpa;

import javafx.util.Pair;
import psl.dauphine.mpsl.base.algorithms.JoinFiveAlgorithm;
import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class NrpaAlgorithm implements JoinFiveAlgorithm {
    private static final double ALPHA = 1;

    @Override
    public Line calcMove(Grid grid) {
        int level = 5;
        NrpaPolicy<Line> policy = new NrpaPolicy<>();
        final long maxRunningTimeMs = 5 * 1000;
        final long endTimeMs = System.currentTimeMillis() + maxRunningTimeMs;
        Pair<Double, List<Line>> result = executeSearch(new NrpaState(grid), level, policy, () -> {

            //System.out.println(System.currentTimeMillis() > endTimeMs);
            return System.currentTimeMillis() > endTimeMs;
        });
        List<Line> lines = result.getValue();
        //System.out.println(lines);
        return lines.size() > 0 ? lines.get(0) : null;
    }

    @Override
    public String getName() {
        return "NRPA";
    }

    public static <TState, TAction> Pair<Double, List<TAction>> executeSearch(INrpaState<TState, TAction> state,
                                                                              final int level, NrpaPolicy<TAction> policy, final Supplier<Boolean> isCanceled) {
        if (level <= 0 || isCanceled.get()) {
            //System.out.println("level 0");
            return nrpaPlayout(state, policy);
        }
        final int N = 10;

        Pair<Double, List<TAction>> bestResult = new Pair<>(0.0, new LinkedList<>());
        for (int i = 0; i < N; i++) {
            Pair<Double, List<TAction>> result = executeSearch(state, level - 1, policy, isCanceled);
            if (result.getKey() >= bestResult.getKey()) {
                bestResult = result;
                policy = nrpaAdapt(policy, state, bestResult);
            }
        }

        return bestResult;
    }

    private static <TAction, TState> Pair<Double, List<TAction>> nrpaPlayout(INrpaState<TState, TAction> state, NrpaPolicy<TAction> policy) {
        ////System.out.println("Playout");
        List<TAction> seq = new LinkedList<>();
        INrpaState<TState, TAction> a, b;
        a = state.copy();
        TAction action;
        while (!a.isTerminalPosition()) {
            ////System.out.println("Working");
            action = nrpaSelectAction(a, policy);
            b = a.takeAction(action);
            seq.add(action);
            if (!b.isTerminalPosition()) {
                action = nrpaSelectAction(b, policy);
                a = b.takeAction(action);
                seq.add(action);
            } else {
                a = b.copy();
            }
        }
        return new Pair<>(a.getScore(), seq);
    }

    private static <TAction, TState> TAction nrpaSelectAction(INrpaState<TState, TAction> state, NrpaPolicy<TAction> policy) {
        ////System.out.println("Selecting Action");
        List<TAction> actions = state.findAllLegalActions();
        ////System.out.println("Found all legal actions");
        int i;
        double[] weights = new double[actions.size()];
        for (i = 0; i < actions.size(); i++) {
            TAction action = actions.get(i);
            ////System.out.println("Weight");
            double value = policy.get(action);
            ////System.out.println("Exp (" + value + ")");
            weights[i] = Math.exp(value) + (i > 0 ? weights[i - 1] : 0);
            ////System.out.println("Done Exp");
            ////System.out.println("done weight");
        }
        double actionWeight = policy.get(actions.get(actions.size() - 1));
        ////System.out.println("Action Weight" + actionWeight);
        double random = Math.random() * Math.exp(actionWeight);

        for (i = 0; i < weights.length; i++) {
            if (weights[i] > random) {
                break;
            }
        }
        return actions.get(i);
    }

    private static <TState, TAction> NrpaPolicy<TAction> nrpaAdapt(NrpaPolicy<TAction> policy, INrpaState<TState, TAction> state, Pair<Double, List<TAction>> bestResult) {
        NrpaPolicy<TAction> polp = policy.copy();
        for (TAction action : bestResult.getValue()) {
            polp.set(action, polp.get(action) + ALPHA);
            double z = 0;
            for (TAction m : state.findAllLegalActions()) {
                z = z + Math.exp(policy.get(m));
            }
            for (TAction m : state.findAllLegalActions()) {
                double newValue = polp.get(m) - ALPHA * Math.exp(policy.get(m)) / z;
                polp.set(m, newValue);
            }
            state = state.takeAction(action);
        }
        return polp;
    }
}

