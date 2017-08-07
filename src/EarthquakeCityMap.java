import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeCityMap extends PApplet {
    private UnfoldingMap map;

    public void setup() {
        size(950, 600, OPENGL);
        map = new UnfoldingMap(this, 200, 50, 700, 500,
                new Google.GoogleMapProvider());
        map.zoomToLevel(2);
        MapUtils.createDefaultEventDispatcher(this, map);

        Location valLoc = new Location(-38.14f, -73.03f);
        PointFeature valEq = new PointFeature(valLoc);
        valEq.addProperty("title", "Valdivia, Chile");
        valEq.addProperty("magnitude", "9.5");
        valEq.addProperty("date", "May 22, 1960");
        valEq.addProperty("year", "1960");

        Location largEq2 = new Location(60.91f, 147.43f);
        PointFeature alaskaEq = new PointFeature(largEq2);
        valEq.addProperty("title", "Southern Alaska");
        valEq.addProperty("magnitude", "9.2");
        valEq.addProperty("date", "March 28, 1964");
        valEq.addProperty("year", "1964");

        Location largEq3 = new Location(3.30f, 95.98f);
        PointFeature sumatraEq = new PointFeature(largEq3);
        valEq.addProperty("title", "Off the West Coast of Northern Sumatra");
        valEq.addProperty("magnitude", "9.1");
        valEq.addProperty("date", "December 26, 2004");
        valEq.addProperty("year", "2004");

        Location largEq4 = new Location(38.30f, 142.37f);
        PointFeature japanEq = new PointFeature(largEq4);
        valEq.addProperty("title", "Near the East Coast of Honshu, Japan");
        valEq.addProperty("magnitude", "9.1");
        valEq.addProperty("date", "March 11, 2011");
        valEq.addProperty("year", "2011");

        Location largEq5 = new Location(52.62f, 159.78f);
        PointFeature kamchatkaEq = new PointFeature(largEq5);
        valEq.addProperty("title", "Off the East Coast of the Kamchatka Peninsula, Russia");
        valEq.addProperty("magnitude", "9.0");
        valEq.addProperty("date", "November 04, 1952");
        valEq.addProperty("year", "1952");



//        Marker val = new SimplePointMarker(valLoc, valEq.getProperties());
//        map.addMarker(val);

        List<PointFeature> bigEqs = new ArrayList<>();
        bigEqs.add(valEq);
        bigEqs.add(alaskaEq);
        bigEqs.add(sumatraEq);
        bigEqs.add(japanEq);
        bigEqs.add(kamchatkaEq);

        List<Marker> markers = new ArrayList<>();
        for(PointFeature eq: bigEqs){
            markers.add(new SimplePointMarker(eq.getLocation(), eq.getProperties()));
        }
        map.addMarkers(markers);


    }

    public void draw(){
        background(10);
        map.draw();
//        addKey();
    }
}
