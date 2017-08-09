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

public class LifeExpectancy extends PApplet{
    UnfoldingMap map;
    Map<String, Float> lifeExpByCountry;
    List<Feature> countries;
    List<Marker> countryMarkers;

    public void setup(){
        size(800,600, OPENGL);
        map = new UnfoldingMap(this, 50,50,700,500, new Google.GoogleMapProvider());
        MapUtils.createDefaultEventDispatcher(this, map);
        countries = new ArrayList<>();
        countryMarkers = new ArrayList<>();
    }

    public void draw(){
        map.draw();
        lifeExpByCountry = loadLifeExpectancyFromCSV("LifeExpectancyWorldBank.csv");
        countries = GeoJSONReader.loadData(this, "countries.geo.json");
        countryMarkers = MapUtils.createSimpleMarkers(countries);
        map.addMarkers(countryMarkers);
        shadeCountries();
    }

    private Map<String, Float> loadLifeExpectancyFromCSV(String filename){
        Map<String, Float> lifeExpMap = new HashMap<>();

        String[] rows = loadStrings(filename);

        for (String row : rows){
            String[] columns = row.split(",");
            if(columns.length >= 5){
                float value = Float.parseFloat(columns[5]);
                lifeExpMap.put(columns[4], value);
            }
        }
        return lifeExpMap;
    }
}
