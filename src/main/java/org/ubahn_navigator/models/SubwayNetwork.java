package org.ubahn_navigator.models;

import org.ubahn_navigator.utils.StationReaderUtil;

import java.util.List;
import java.util.Map;

//U-Bahn-Netz
public class SubwayNetwork {
    //Linie Stationen - Map der Linie und die Liste der Stationen von Linie.
    private Map<String, List<Station>> lineStations;

    //Konstruktor, der Array von Linien als Parameter nimmt.
    public SubwayNetwork(String ... lines){
        lineStations = StationReaderUtil.getLineStationsFromFiles(lines);
    }

    //Station nach Name holen.
    public Station getStationByName(String name){
        for (Map.Entry<String, List<Station>> lineStationsPair : lineStations.entrySet()){
            for (Station station : lineStationsPair.getValue()){
                //Ob die gesuchte Station gefunden wird.
                if (station.getName().equalsIgnoreCase(name)){
                    //Die gesuchte Station wird zurückgegeben.
                    return station;
                }
            }
        }
        //Eine IllegalArgumentException wird ausgelöst.
        throw new IllegalArgumentException("Es wurde keine Station mit Namen[" + name + " gefunden");
    }

    //Linie Stationen holen.
    public Map<String, List<Station>> getLineStations(){
        return lineStations;
    }
}