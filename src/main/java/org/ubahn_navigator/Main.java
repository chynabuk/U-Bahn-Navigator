package org.ubahn_navigator;

import org.ubahn_navigator.algorithms.DijkstraAlgorithm;
import org.ubahn_navigator.algorithms.ShortestPathFindingAlgorithm;
import org.ubahn_navigator.models.Station;
import org.ubahn_navigator.models.SubwayNetwork;
import org.ubahn_navigator.utils.CmdUtil;
import org.ubahn_navigator.utils.StationsEditorUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Das Objekt der Klasse "SubwayNetwork" wird erstellt und mithilfe der Methode der Klasse "CmdUtil" die Dateinamen übergeben.
        SubwayNetwork subwayNetwork = new SubwayNetwork(
                CmdUtil.extractFileNames()
        );

        //Die Nachbarn von Stationen werden eingerichtet
        StationsEditorUtil.setupStationNeighbours(subwayNetwork.getLineStations());

        //Der Algorithmus von Dijkstra wird erstellt
        ShortestPathFindingAlgorithm dijkstraAlgorithm = DijkstraAlgorithm.getInstance();

        ///Das Objekt der Klasse "Scanner" wird erstellt, um Daten von der Konsole einzugeben.
        Scanner scanner = new Scanner(System.in);
        String startString;
        String zielString;

        while (true){
            System.out.println("=====================================");
            System.out.println("Eingabe:");

            //Einen Wert für die Startstation wird eingegeben.
            System.out.print("  Start: ");
            startString = scanner.nextLine();

            //Einen Wert für die Zielstation wird eingegeben.
            System.out.print("  Ziel: ");
            zielString = scanner.nextLine();

            try {
                //Die Stationsobjekte werden mithilfe von Eingabewerten geholt.
                Station start = subwayNetwork.getStationByName(startString);
                Station ziel = subwayNetwork.getStationByName(zielString);

                //Das Ergebnis des Dijkstra-Algorithmus wird angezeigt.
                System.out.println("  " + dijkstraAlgorithm.getShortestPath(start, ziel));

                //Alle Änderungen an den Stationen, die mit der Entfernung und dem kürzesten Weg verbunden sind, werden zurückgesetzt.
                StationsEditorUtil.resetDistanceShortestAndPathChanges(subwayNetwork.getLineStations());
            }
            catch (Exception e){
                //Wenn eine Ausnahme ausgelöst wird, wird Meldung angezeigt.
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println("=====================================");
            //Wenn man der Wert "Nein" eingibt, wird die Schleife gestoppt.
            System.out.print("Möchten Sie das Programm fortsetzen(Ja/Nein): ");
            if (scanner.nextLine().equalsIgnoreCase("Nein")){
                break;
            }
        }

    }
}
