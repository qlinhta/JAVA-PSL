package psl.dauphine.mpsl.base.algorithms;

import psl.dauphine.mpsl.base.algorithms.nmcs.NmcsAlgorithm;
import psl.dauphine.mpsl.base.algorithms.nrpa.NrpaAlgorithm;

import java.util.List;

/**
 * Simplifies selecting an algorithm from the available algorithm implementations
 */
public class AlgorithmFactory {

    public static List<String> availableAlgorithmNames() {
        return List.of("Random Search", "NMCS", "NRPA");
    }

    /**
     * Gives an algorithm from its name
     *
     * @param algorithmName name of the algorithm
     * @return the algorithm
     */
    public static JoinFiveAlgorithm createAlgorithm(String algorithmName) {
        algorithmName = algorithmName.toLowerCase();
        System.out.println(algorithmName);
        switch (algorithmName) {
            case "random search" -> {
                return new RandomSearchAlgorithm();
            }
            case "nmcs" -> {
                return new NmcsAlgorithm();
            }
            case "nrpa" -> {
                return new NrpaAlgorithm();
            }
            default -> {
                return null;
            }
        }
    }
}
