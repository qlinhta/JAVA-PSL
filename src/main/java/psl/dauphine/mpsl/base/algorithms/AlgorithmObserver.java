package psl.dauphine.mpsl.base.algorithms;

public interface AlgorithmObserver {
    void done();

    void updateTimeElapsed(long timeElapsed);
}
