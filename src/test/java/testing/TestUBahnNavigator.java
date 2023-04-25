package testing;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.ubahn_navigator.algorithms.DijkstraAlgorithm;
import org.ubahn_navigator.algorithms.ShortestPathFindingAlgorithm;
import org.ubahn_navigator.models.Station;
import org.ubahn_navigator.models.SubwayNetwork;
import org.ubahn_navigator.utils.CmdUtil;
import org.ubahn_navigator.utils.StationsEditorUtil;

public class TestUBahnNavigator {
    private SubwayNetwork subwayNetwork;
    private ShortestPathFindingAlgorithm dijkstraAlgorithm;
    private String[] lines;

    //Die wichtigen Objekte erstellen.
    @BeforeEach
    void setup(){
        lines = CmdUtil.extractFileNames();

        subwayNetwork = new SubwayNetwork(lines);
        StationsEditorUtil.setupStationNeighbours(subwayNetwork.getLineStations());

        dijkstraAlgorithm = DijkstraAlgorithm.getInstance();
    }

    /*
    Die Testmethode "stationLineListHashMapShouldBeNotEmpty" prüft,
    ob Map der Linie und die Liste der Stationen von Linie nicht leer ist.
     */
    @Test
    void stationLineListHashMapShouldBeNotEmpty(){
        Assertions.assertTrue(!subwayNetwork.getLineStations().isEmpty());
    }

    /*
    Die Testmethode "stationsByLineShouldContainThreeStationLines" prüft,
    ob alle Linien in Map der Linie und die Liste der Stationen von Linie enthalten.
     */
    @Test
    void stationsByLineShouldContainStationLines(){
        for (String line : lines){
            String l = line.split("[.]", 0)[0];
            Assertions.assertTrue(subwayNetwork.getLineStations().containsKey(l));
        }
    }

    /*
    Die Testmethode "stationsOfEachLineShouldBeNotEmpty" prüft,
    ob die Stationen jeder Linie nicht leer sind.
     */
    @Test
    void stationsOfEachLineShouldBeNotEmpty(){
        for (String line : lines){
            String l = line.split("[.]", 0)[0];
            Assertions.assertTrue(!subwayNetwork.getLineStations().get(l).isEmpty());
        }
    }

    /*
    Die Testmethode "stationShouldHaveNeighbours" prüft,
    ob die Station die Nachbarstationen hat.
     */
    @Test
    void stationShouldHaveNeighbours(){
        subwayNetwork.getLineStations().forEach((line, stations) -> {
            stations.forEach(station -> Assertions.assertTrue(!station.getNeighbourStations().isEmpty()));
        });
    }

    /*
    Die Testmethode "getStationByNameMethodShouldReturnNotNullWhenGivenCorrectValue" prüft,
    ob die zurückgegebene Station nicht null ist, wenn der richtige Wert übergeben wird.
     */
    @Test
    void getStationByNameMethodShouldReturnNotNullWhenGivenCorrectValue(){
        Assertions.assertNotNull(subwayNetwork.getStationByName("Toshkent"));
    }

    /*
    Die Testmethode "getStationByNameMethodShouldThrowIllegalArgumentExceptionWhenGivenIncorrectValue" prüft,
    ob eine IllegalArgumentException wird ausgelöst, wenn der nicht richtige Wert übergeben wird.
     */
    @Test
    void getStationByNameMethodShouldThrowIllegalArgumentExceptionWhenGivenIncorrectValue(){
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                subwayNetwork.getStationByName("Toshkent2");
            }
        });
    }

    /*
    Die Testmethode "shouldReturnShortestPath" prüft, ob die richtigen Ergebnisse zurückgegeben werden
     */
    @Test
    void shouldReturnShortestPath(){
        Assertions.assertArrayEquals(
                preparePath(
                        "Beruni (Uzbekistan Line)",
                        "Tinchlik (Uzbekistan Line)",
                        "Chorsu (Uzbekistan Line)",
                        "Gafur Gulom (Uzbekistan Line)",
                        "Paxtakor-Alisher Navoiy (Uzbekistan Line)",
                        "Uzbekistan (Uzbekistan Line)",
                        "Kosmonavtlar (Uzbekistan Line)",
                        "Ming Urik - Oybek (Uzbekistan Line)",
                        "Toshkent (Uzbekistan Line)",
                        "Mashinasozlar (Uzbekistan Line)",
                        "Dustlik (Uzbekistan Line)"
                ),
                getShortestPath("Beruni", "Dustlik")
        );


        Assertions.assertArrayEquals(
                preparePath(
                        "Kosmonavtlar (Uzbekistan Line)"
                ),
                getShortestPath("Kosmonavtlar", "Kosmonavtlar")
        );

        Assertions.assertArrayEquals(
                preparePath(
                        "Kosmonavtlar (Uzbekistan Line)",
                        "Ming Urik - Oybek (Uzbekistan Line)"
                ),
                getShortestPath("Kosmonavtlar", "Ming Urik - Oybek")
        );

        Assertions.assertArrayEquals(
                preparePath(
                        "Toshkent (Uzbekistan Line)",
                        "Ming Urik - Oybek (Change to Yunusobod Line)",
                        "Amir Temur Xiyoboni-Yunus Rajabiy (Change to Chilonzor Line)",
                        "Hamid Olimjon (Chilonzor Line)",
                        "Pushkin (Chilonzor Line)"
                ),
                getShortestPath("Toshkent", "Pushkin")
        );

        Assertions.assertArrayEquals(
                preparePath(
                        "Olmazar (Chilonzor Line)",
                        "Chilonzor (Chilonzor Line)",
                        "Mirzo Ulugbek (Chilonzor Line)",
                        "Novza (Chilonzor Line)",
                        "Milliy Bog (Chilonzor Line)",
                        "Bunyodkor (Chilonzor Line)",
                        "Paxtakor-Alisher Navoiy (Chilonzor Line)",
                        "Mustaqilliq Maidoni (Chilonzor Line)",
                        "Amir Temur Xiyoboni-Yunus Rajabiy (Change to Yunusobod Line)",
                        "Abdulla Qodirii (Yunusobod Line)",
                        "Minor (Yunusobod Line)",
                        "Bodomzor (Yunusobod Line)",
                        "Shahriston (Yunusobod Line)"),
                getShortestPath("Olmazar", "Shahriston")
        );

        Assertions.assertArrayEquals(
                preparePath(
                        "Buyuk Ipak Yuli (Chilonzor Line)",
                        "Pushkin (Chilonzor Line)",
                        "Hamid Olimjon (Chilonzor Line)",
                        "Amir Temur Xiyoboni-Yunus Rajabiy (Change to Yunusobod Line)",
                        "Ming Urik - Oybek (Change to Uzbekistan Line)",
                        "Toshkent (Uzbekistan Line)",
                        "Mashinasozlar (Uzbekistan Line)",
                        "Dustlik (Uzbekistan Line)"
                ),
                getShortestPath("Buyuk Ipak Yuli", "Dustlik")
        );

        Assertions.assertArrayEquals(
                preparePath(
                        "Ming Urik - Oybek (Uzbekistan Line)"
                ),
                getShortestPath("Ming Urik - Oybek", "Ming Urik - Oybek")
        );
    }

    private String[] preparePath(String ... path){
        return path;
    }

    private String[] getShortestPath(String s, String z){
        StationsEditorUtil.resetDistanceShortestAndPathChanges(subwayNetwork.getLineStations());
        Station start = subwayNetwork.getStationByName(s);
        Station ziel = subwayNetwork.getStationByName(z);
        return dijkstraAlgorithm.getShortestPath(start, ziel).split("\n");
    }
}