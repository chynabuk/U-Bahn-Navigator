package org.ubahn_navigator.utils;

import org.ubahn_navigator.models.Station;

import java.util.List;
import java.util.Map;

//die Klasse "" bietet die Bearbeitung von Stationsobjekte
public class StationsEditorUtil {
    /*
    Die Methode "resetDistanceShortestAndPathChanges" dient dazu,
    alle Stationen in Map zurückzusetzen,
    indem ihre Distanz auf den Maximalwert gesetzt und der kürzeste Pfad(Weg) geleert wird.
    */
    public static void resetDistanceShortestAndPathChanges(Map<String, List<Station>> lineStations){
        lineStations.forEach((line, stations) -> {
            stations.forEach(station -> {
                station.setDistance(Integer.MAX_VALUE);
                station.getShortestPath().clear();
            });
        });
    }

    /*
    Die Methode nimmt eine Map lineStations, die für jeden gegebenen Liniennamen eine Liste von Stationen enthält
    und diese Methode implementiert die Logik zum Definieren von Nachbarstationen für alle Stationen.
    */
    public static void setupStationNeighbours(Map<String, List<Station>> lineStations) {
        /*
        Die forEach Schleife durchläuft jede Linie in der lineStations Map,
        für jede Linie ruft sie die Liste von Stationen ab.
        */
        lineStations.forEach((line, stations) -> {

            //Für jede Station werden folgende Operationen ausgeführt.
            for (int i = 0; i < stations.size(); i++){

                //Ob i weniger als die Differenz von Größe der StationenListe und 1.
                if (i < stations.size() - 1) {

                    //Die nächste Station wird zur aktuellen Station als Nachbar hinzugefügt.
                    stations.get(i).addNeighbourStation(stations.get(i + 1));
                }

                //Ob 0 weniger als i und i weniger als Größe der StationenListe
                if (0 < i && i < stations.size()){

                    //Die vorherige Station wird zur aktuellen Station als Nachbar hinzugefügt.
                    stations.get(i).addNeighbourStation(stations.get(i - 1));
                }

                //Ob die aktuelle Station Übertragungsstation ist.
                if (stations.get(i).isTransferStation()){

                    //Die Methode "setupTransferStationNeighbour" wird ausgeführt.
                    setupTransferStationNeighbour(lineStations, line, stations, i);
                }
            }
        });
    }


    /*
    Die Methode nimmt eine Map mit Linien und
    deren Stationen sowie die aktuelle Linie, die Liste der Stationen und den aktuellen Index.
    */
    private static void setupTransferStationNeighbour(Map<String, List<Station>> lineStations, String line, List<Station> stations, int i){
        //Die forEach Schleife durchläuft die Map der Linien und deren Stationen.
        for (Map.Entry<String, List<Station>> lineStationsPair : lineStations.entrySet()){

            //Hier wird überprüft, ob die aktuelle Linie mit der durchlaufenden Linie übereinstimmt.
            if (line.equals(lineStationsPair.getKey())){
                //Die Schleife wird übersprungen.
                continue;
            }

            //Die Stationen werden der durchlaufenden Linie abgerufen.
            List<Station> stations2 = lineStationsPair.getValue();

            //Eine Schleife durch diese Stationen gestartet.
            for (Station station2 : stations2){
                /*
                Für jede Station der durchlaufenden Linie wird überprüft,
                ob ihr Name mit dem Namen der aktuellen Station übereinstimmt.
                */
                if (stations.get(i).getName().equals(station2.getName())){
                    //Die Station der aktuellen Station wird als Nachbar hinzugefügt.
                    stations.get(i).addNeighbourStation(station2);
                }
            }
        }
    }
}