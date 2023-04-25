package org.ubahn_navigator.algorithms;

import org.ubahn_navigator.models.Station;

public interface ShortestPathFindingAlgorithm {
    String getShortestPath(Station start, Station goal);
}