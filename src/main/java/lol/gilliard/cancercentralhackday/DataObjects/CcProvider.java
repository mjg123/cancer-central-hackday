package lol.gilliard.cancercentralhackday.DataObjects;

import java.util.ArrayList;
import java.util.List;

public class CcProvider {

    public final int id;
    public final String name;
    public final String description;

    public final Double lat, lon;
    public double distanceKm;

    public List<String> categories = new ArrayList<>();
    public List<String> cancerTypes = new ArrayList<>();
    public List<String> keywords = new ArrayList<>();

    public CcProvider(int id, String name, String description, Double lat, Double lon) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
    }

    public void setDistance(double distance) {
        this.distanceKm = distance;
    }
}
