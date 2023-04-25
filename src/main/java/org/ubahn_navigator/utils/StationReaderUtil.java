package org.ubahn_navigator.utils;

import org.ubahn_navigator.models.Station;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Die Klasse "StationReaderUtil" bietet, die Dateien zu lesen
public class StationReaderUtil {

    //Linie Stationen aus Dateien holen.
    public static Map<String, List<Station>> getLineStationsFromFiles(String ... fileNames){
        Map<String, List<Station>> lineStations = collectToMap(fileNames);
        return lineStations;
    }

    //Liste, die von der readFile-Methode erhalten wird, in Map sammeln und zurückgeben.
    private static Map<String, List<Station>> collectToMap(String... fileNames){
        //HashMap mit dem Schlüssel von String und dem Wert von Liste der Stationen wird erstellt.
        Map<String, List<Station>> lineStations = new HashMap<>();

        for (String fileName : fileNames){
            //Dateinamen mit Punkt teilen, um Dateinamen ohne Dateiformat zu erhalten.
            String line = fileName.split("[.]", 0)[0];

            //Die Liste von Stationen, die von der readFile-Methode erhalten wird, zu lineStations hinzufügen.
            lineStations.put(line, readFile(fileName, line));
        }

        return lineStations;
    }

    //Datei lesen und Liste von Stationen zurückgeben.
    private static List<Station> readFile(String fileName, String line){
        //Liste von Stationen wird erstellt.
        List<Station> stations = new ArrayList<>();

        try{
            String station;

            //Objekt der Klasse BufferedReader wird definiert, indem den Dateinamen dorthin übergeben.
            BufferedReader bf = new BufferedReader(new FileReader("Lines/" + fileName));

            /*
            While-Schleife wird gestartet und den Inhalt der Datei gelesen.
            Solange station nicht Null ist, wird der folgende Codeblock ausgeführt.
            */
            while ((station = bf.readLine()) != null){

                //Ob station(String) das Zeichen "-" umfasst.
                if (station.contains("-")){

                    //Objekt der Klasse Station wird erstellt, wobei das Attribut isTransferStation true ist.
                    stations.add(new Station(station, true, line));
                }
                else{
                    //Objekt der Klasse Station wird erstellt, wobei das Attribut isTransferStation false ist.
                    stations.add(new Station(station, false, line));
                }
            }
        }
        catch (FileNotFoundException fnf){
            System.out.println(fnf.getMessage());
        }
        catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
        return stations;
    }
}