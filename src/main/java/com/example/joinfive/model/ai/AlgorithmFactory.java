package com.example.joinfive.model.ai;

import java.util.List;

public class AlgorithmFactory {

    public static List<String> availableAlgorithmNames() {
        return List.of("Random Search ", "NMCS");
    }

    public static JoinFiveAlgorithm createAlgorithm(String algorithmName) {
        algorithmName = algorithmName.toLowerCase();
        System.out.println(algorithmName);
        switch (algorithmName) {
            case "random search" -> {
                return new RandomSearchAlgorithm();
            }
            case "nmcs" -> {
                return new NestedMonteCarloSearchAlgorithm();
            }
            default -> {
                return null;
            }
        }
    }
}
