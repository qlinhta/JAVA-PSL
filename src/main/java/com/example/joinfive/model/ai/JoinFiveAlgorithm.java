package com.example.joinfive.model.ai;

import com.example.joinfive.model.grid.Grid;
import com.example.joinfive.model.grid.Line;

public interface JoinFiveAlgorithm {
    Line calcMove(Grid grid);
}
