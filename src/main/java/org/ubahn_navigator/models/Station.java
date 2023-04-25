package org.ubahn_navigator.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Station {
    private String name;

    //Kürzester Pfad(Weg).
    private List<Station> shortestPath;

    //Map der Nachbarstation und Distanz von ihnen.
    private Map<Station, Integer> neighbourStations;

    //Distanz.
    private Integer distance;

    //Ist Übertragungsstation.
    private boolean isTransferStation;

    //Linie.
    private String line;

    //Konstruktor, der 3 Parameter nimmt: der Name, die isTransferStation, die Linie.
    public Station(String name, boolean isTransferStation, String line){
        this.name = name;
        shortestPath = new ArrayList<>();
        neighbourStations = new HashMap<>();
        distance = Integer.MAX_VALUE;
        this.isTransferStation = isTransferStation;
        this.line = line;
    }

    //Nachbarstation hinzufügen.
    public void addNeighbourStation(Station neighbourStation) {
        neighbourStations.put(neighbourStation, 1);
    }

    //Name holen.
    public String getName() {
        return name;
    }

    //Kürzester Weg holen.
    public List<Station> getShortestPath() {
        return shortestPath;
    }

    //Kürzester Weg setzen.
    public void setShortestPath(List<Station> shortestPath) {
        this.shortestPath = shortestPath;
    }

    //Nachbarstationen holen.
    public Map<Station, Integer> getNeighbourStations() {
        return neighbourStations;
    }

    //Distanz holen.
    public Integer getDistance() {
        return distance;
    }

    //Distanz setzen.
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    //Ist Übertragungsstation.
    public boolean isTransferStation() {
        return isTransferStation;
    }

    //Linie holen.
    public String getLine() {
        return line;
    }

}