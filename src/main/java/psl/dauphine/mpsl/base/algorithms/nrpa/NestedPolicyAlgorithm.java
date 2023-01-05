package psl.dauphine.mpsl.base.algorithms.nrpa;

import javafx.util.Pair;
import psl.dauphine.mpsl.base.algorithms.JoinFiveAlgorithm;
import psl.dauphine.mpsl.base.grid.Grid;
import psl.dauphine.mpsl.base.grid.Line;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class NestedPolicyAlgorithm implements JoinFiveAlgorithm {
    private static final double ALPHA = 1;

    @Override
    public Line calcMove(Grid grid) {
        int level = 5;
        NestedPolicySetup<Line> policy = new NestedPolicySetup<>();
        final long maxRunningTimeMs = 60 * 1000;
        final long endTimeMs = System.currentTimeMillis() + maxRunningTimeMs;
        Pair<Double, List<Line>> result = _searchNestedPolicy(new NestedPolicyState(grid), level, policy, () -> {
            return System.currentTimeMillis() > endTimeMs;
        });
        List<Line> lines = result.getValue();
        return lines.size() > 0 ? lines.get(0) : null;
    }

    @Override
    public String getName() {
        return "NRPA";
    }

    /**
     * Performs a nested policy search on the given state. The search continues until the specified level is reached or the
     * cancellation supplier returns true. At each level, the method iteratively performs NRPA playouts and adapts the
     * policy using the NRPA adaptation method. The best result from all playouts is returned.
     *
     * @param state      The state to perform the search on.
     * @param level      The level to search to.
     * @param policy     The policy to use for the search.
     * @param isCanceled A supplier that returns true if the search should be canceled.
     * @param <State>    The type of the state.
     * @param <Action>   The type of the actions.
     * @return A pair containing the score of the best result and the list of actions leading to that result.
     */
    public static <State, Action> Pair<Double, List<Action>> _searchNestedPolicy(InterfNestedPolicySearch<State, Action> state,
                                                                                 final int level, NestedPolicySetup<Action> policy, final Supplier<Boolean> isCanceled) {
        if (level <= 0 || isCanceled.get()) {
            return nrpaPlayout(state, policy);
        }
        final int N = 10;

        Pair<Double, List<Action>> bestResult = new Pair<>(0.0, new LinkedList<>());
        for (int i = 0; i < N; i++) {
            Pair<Double, List<Action>> result = _searchNestedPolicy(state, level - 1, policy, isCanceled);
            if (result.getKey() >= bestResult.getKey()) {
                bestResult = result;
                policy = nrpaAdapt(policy, state, bestResult);
            }
        }
        return bestResult;
    }

    /**
     * Performs a playout from the given state using the given policy.
     *
     * @param state  the current state of the game
     * @param policy the policy to use for action selection
     * @return a pair containing the score at the terminal state and the sequence of actions taken during the playout
     */
    private static <Action, State> Pair<Double, List<Action>> nrpaPlayout(InterfNestedPolicySearch<State, Action> state, NestedPolicySetup<Action> policy) {
        List<Action> seq = new LinkedList<>();
        InterfNestedPolicySearch<State, Action> a, b;
        a = state.copy();
        Action action;
        while (!a.isTerminalPosition()) {
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
    
    /**
     * Selects an action from the available legal actions using the given policy.
     *
     * @param state  the current state of the game
     * @param policy the policy to use for action selection
     * @return the selected action
     */

    private static <Action, State> Action nrpaSelectAction(InterfNestedPolicySearch<State, Action> state, NestedPolicySetup<Action> policy) {
        List<Action> actions = state.findAllLegalActions();
        int i;
        double[] weights = new double[actions.size()];
        for (i = 0; i < actions.size(); i++) {
            Action action = actions.get(i);
            double value = policy.get(action);
            weights[i] = Math.exp(value) + (i > 0 ? weights[i - 1] : 0);
        }
        double actionWeight = policy.get(actions.get(actions.size() - 1));
        double random = Math.random() * Math.exp(actionWeight);

        for (i = 0; i < weights.length; i++) {
            if (weights[i] > random) {
                break;
            }
        }
        return actions.get(i);
    }
    
    /**
     * Adapts the given policy based on the best result obtained from a playout.
     *
     * @param policy     the policy to adapt
     * @param state      the current state of the game
     * @param bestResult the best result obtained from a playout
     * @return the adapted policy
     */

    private static <State, Action> NestedPolicySetup<Action> nrpaAdapt(NestedPolicySetup<Action> policy, InterfNestedPolicySearch<State, Action> state, Pair<Double, List<Action>> bestResult) {
        NestedPolicySetup<Action> polp = policy.copy();
        for (Action action : bestResult.getValue()) {
            polp.set(action, polp.get(action) + ALPHA);
            double z = 0;
            for (Action m : state.findAllLegalActions()) {
                z = z + Math.exp(policy.get(m));
            }
            for (Action m : state.findAllLegalActions()) {
                double newValue = polp.get(m) - ALPHA * Math.exp(policy.get(m)) / z;
                polp.set(m, newValue);
            }
            state = state.takeAction(action);
        }
        return polp;
    }
}

