package org.ubahn_navigator.algorithms;

import org.ubahn_navigator.models.Station;

import java.util.ArrayList;
import java.util.List;

public class DijkstraAlgorithm implements ShortestPathFindingAlgorithm {
    private static DijkstraAlgorithm dijkstra;
    private DijkstraAlgorithm(){
    }

    public static DijkstraAlgorithm getInstance(){
        if (dijkstra == null){
            dijkstra = new DijkstraAlgorithm();
        }
        return dijkstra;
    }

    /*
    Dies ist eine Methode, die den kürzesten Weg zwischen zwei Stationen berechnet und zurückgibt.
    Die Methode nimmt zwei Stationsobjekte - start und ziel - als Parameter.
    */
    @Override
    public String getShortestPath(Station start, Station ziel) {
        //Die Methode "calculateShortestPath" wird aufgerufen.
        calculateShortestPath(start, ziel);

        //Den kürzesten Weg aus dem Ziel-Stationsobjekt wird geholt und ihn in der Liste "shortestPath" gespeichert.
        List<Station> shortestPath = ziel.getShortestPath();

        /*
        Die String Variable "result(Ergebnis)" wird mit Leere Zeichenfolge erstellt.
        In dieser Variable werden die Informationen des kürzesten Weges vom Start zum Ziel gespeichert.
        */
        String result = "";

        /*
        Die boolesche Variable "transferred" wird erstellt
        und verwendet, um Fälle zu behandeln, in denen von einer in eine andere Linie übertragen wird
        */
        boolean transferred = false;

        //Die For-Schleife iteriert über jede Station in der Liste "shortestPath"
        for (int i = 0; i < shortestPath.size(); i++){

            //Ob in der vorherigen Iteration bereits eine Station übertragen wurde.
            if (transferred){

                //Die Schleife wird übersprungen, indem der Wert "false" auf Variable "transferred" gesetzt.
                transferred = false;
                continue;
            }

            //Ob die aktuelle Station nicht die letzte Station in der Liste ist.
            if (i < shortestPath.size() - 1){

                //Ob die aktuelle Station denselben Namen wie die nächste Station hat.
                if (shortestPath.get(i).getName().equals(shortestPath.get(i + 1).getName())){

                    //Ob i gleich 0 ist, wird die Schleife übersprungen.
                    if (i == 0){
                        continue;
                    }

                    //Der Stationsname und die Linie, in die geändert werden muss, werden zum Ergebnis hinzugefügt.
                    result += shortestPath.get(i).getName() + " (Change to " + shortestPath.get(i + 1).getLine() + ")\n";

                    //Die Variable "transferred" wird auf true gesetzt.
                    transferred = true;

                    //Die Schleife wird übersprungen.
                    continue;
                }
            }

            //Der Name der Station und die Linie werden zum Ergebnis hinzugefügt.
            result += shortestPath.get(i).getName() + " (" + shortestPath.get(i).getLine() + ")\n";
        }
        //Ob die Liste "shortestPath" nicht leer ist.
        if (!shortestPath.isEmpty()){

            //Ob die letzte Station in der Liste "shortestPath" nicht die Zielstation ist.
            if(!shortestPath.get(shortestPath.size() - 1).getName().equals(ziel.getName())){

                //Die Zielstation wird mit ihren Linieninformationen zum Ergebnis hinzugefügt.
                result += ziel.getName() + " (" + ziel.getLine() + ")";
            }
        }
        else{
            //Die Startstation wird mit ihren Linieninformationen zum Ergebnis hinzugefügt.
            result = start.getName() + " (" + start.getLine() + ")";
        }

        //Das Ergebnis wird zurückgegeben.
        return result;
    }

    //Die Methode berechnet den kürzesten Weg vom Startstation zur Zielstation.
    private void calculateShortestPath(Station start, Station ziel) {
        //Die Distanz der Startstation auf 0 wird gesetzt.
        start.setDistance(0);

        /*
        Zwei Listen werden initialisiert, settledStations und unsettledStations

        settledStations - visitedStations - besuchten Stationen.
        settledStations enthält Stationen, deren kürzester Pfad bereits berechnet wurde.
         */
        List<Station> settledStations = new ArrayList<>();

        /*
        unsettledStations - unvisitedStations - nicht besuchten Stationen.
        unsettledStations enthält alle Stationen, die noch nicht berechnet wurden.
         */
        List<Station> unsettledStations = new ArrayList<>();

        //Die Startstation wird zur Liste "unsettledStations" hinzugefügt.
        unsettledStations.add(start);

        Station current = start;

        //While-Schleife wird fortgesetzt, solange die aktuelle Station nicht die Zielstation ist.
        while (!current.equals(ziel)) {
            /*
            In jeder Iteration wird zuerst mit der Methode "getLowestDistanceStation"
            die Station mit der geringsten Distanz in der Liste "unsettledStations" gefunden.
            */
            current = getLowestDistanceStation(unsettledStations);
            Station currentStation = current;

            //Diese Station wird aus der Liste "unsettledStations entfernt".
            unsettledStations.remove(current);

            //Es wird über die Nachbarstationen der aktuellen Station iteriert.
            current.getNeighbourStations().forEach((neighbourStation, edgeWeight) -> {

                //Ob eine Nachbarstation nicht in der Liste "settledStations" enthalten ist.
                if (!settledStations.contains(neighbourStation)) {

                    //Die Mindestdistanz wird zu dieser Nachbarstation mit der Methode "calculateMinimumDistance()" berechnet.
                    calculateMinimumDistance(neighbourStation, edgeWeight, currentStation);

                    //Die Nachbarstation wird zur Liste "unsettledStations" hinzugefügt.
                    unsettledStations.add(neighbourStation);
                }
            });

            //Die aktuelle Station wird zur Liste "settledStations" hinzugefügt.
            settledStations.add(current);
        }
    }

    //Die Methode nimmt die Liste unsettledStations als Parameter und zurückgibt die Station mit der geringsten Distanz.
    private Station getLowestDistanceStation(List<Station> unsettledStations) {
        Station lowestDistanceStation = null;
        int lowestDistance = Integer.MAX_VALUE;

        //Alle Stationen in der Liste werden durchlaufen.
        for (Station station : unsettledStations) {
            int stationDistance = station.getDistance();

            //Für jede Station wird überprüft, ob ihre Distanz kleiner als die bisher kleinste Distanz ist.
            if (stationDistance < lowestDistance) {

                //Station wird als die Station mit der geringsten Distanz aktualisiert.
                lowestDistance = stationDistance;
                lowestDistanceStation = station;
            }
        }
        return lowestDistanceStation;
    }

    /*
    Die Methode berechnet die Mindestdistanz zwischen zwei Stationen.
    Die Methode nimmt drei Parameter: die Station, die ausgewertet wird,
    die Distanz, die die Bewertungsstation(evaluationStation) mit der Quellstation(sourceStation) verbindet,
    und die Quellstation.
    */
    private void calculateMinimumDistance(Station evaluationStation, Integer interStationDistance, Station sourceStation) {
        //Distanz der Quellenstation wird geholt.
        Integer sourceDistance = sourceStation.getDistance();

        int sumOfSourceDistanceAndInterStationDistance = sourceDistance + interStationDistance;

        //Ob die Summe aus Quelldistanz und Distanz zwischen Stationen kleiner als die Distanz der Bewertungsstation ist.
        if (sumOfSourceDistanceAndInterStationDistance < evaluationStation.getDistance()) {

            //Die Distanz der Bewertungstation wird mit der Summe aktualisiert
            evaluationStation.setDistance(sumOfSourceDistanceAndInterStationDistance);

            //und ihren kürzesten Weg wird aktualisiert.
            List<Station> shortestPath = new ArrayList<>(sourceStation.getShortestPath());
            shortestPath.add(sourceStation);
            evaluationStation.setShortestPath(shortestPath);
        }
    }
}