import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LifeExpectancy extends PApplet {
    private UnfoldingMap map;
    private Map<String, Float> lifeExpByCountry;
    private List<Feature> countries;
    private List<Marker> countryMarkers;

    public void setup() {
        size(800, 600, OPENGL);
        map = new UnfoldingMap(this, 50, 50, 700, 500, new Google.GoogleMapProvider());
        MapUtils.createDefaultEventDispatcher(this, map);
        countries = new ArrayList<>();
        countryMarkers = new ArrayList<>();

        lifeExpByCountry = loadLifeExpectancyFromCSV("LifeExpectancyWorldBankModule3.csv");
        println("Loaded " + lifeExpByCountry.size() + " data entries");
        countries = GeoJSONReader.loadData(this, "countries.geo.json");
        countryMarkers = MapUtils.createSimpleMarkers(countries);
        map.addMarkers(countryMarkers);
        shadeCountries();
    }

    public void draw() {
        map.draw();
    }

    private void shadeCountries() {
        for (Marker marker : countryMarkers) {
            String countryId = marker.getId();

            if (lifeExpByCountry.containsKey(countryId)) {
                float lifeExp = lifeExpByCountry.get(countryId);
                int colorLevl = (int) map(lifeExp, 40, 90, 10, 255);
                marker.setColor(color(255 - colorLevl, 100, colorLevl));
            } else {
                marker.setColor(color(150,150,150));
            }
        }
    }

    private Map<String, Float> loadLifeExpectancyFromCSV(String filename) {
        Map<String, Float> lifeExpMap = new HashMap<>();

        String[] rows = loadStrings(filename);

        for (String row : rows) {
            String[] columns = row.split(",");
            if (columns.length == 6 && !columns[5].equals("..")) {
                float value = Float.parseFloat(columns[5]);
                lifeExpMap.put(columns[4], value);
            }
        }
        return lifeExpMap;
    }
}
