package psl.dauphine.mpsl.base.algorithms.nrpa;

import java.util.HashMap;

public class NestedPolicySetup<Action> {

    private final HashMap<String, Double> actionsMap;

    public NestedPolicySetup() {
        actionsMap = new HashMap<>();
    }

    public synchronized double get(Action action) {
        Double value = actionsMap.computeIfAbsent(action.toString(), k -> 0.0);
        return value;
    }

    public synchronized NestedPolicySetup<Action> copy() {
        NestedPolicySetup<Action> np = new NestedPolicySetup<>();
        np.actionsMap.putAll(actionsMap);
        return np;
    }

    public synchronized void set(Action action, double v) {
        actionsMap.put(action.toString(), v);
    }
}
