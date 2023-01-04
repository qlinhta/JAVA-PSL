package psl.dauphine.mpsl.base.algorithms.nrpa;

import java.util.HashMap;

public class NestedPolicySetup<TAction> {

    private final HashMap<String, Double> actionsMap;

    public NestedPolicySetup() {
        actionsMap = new HashMap<>();
    }

    public synchronized double get(TAction tAction) {
        Double value = actionsMap.computeIfAbsent(tAction.toString(), k -> 0.0);
        return value;
    }

    public synchronized NestedPolicySetup<TAction> copy() {
        NestedPolicySetup<TAction> np = new NestedPolicySetup<>();
        np.actionsMap.putAll(actionsMap);
        return np;
    }

    public synchronized void set(TAction tAction, double v) {
        actionsMap.put(tAction.toString(), v);
    }
}
