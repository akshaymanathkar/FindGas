package findgas.com.findgas.station;

import java.util.ArrayList;

/**
 * Created by akshaymanathkar on 31/05/16.
 */
public class StationData {
    private Status status;
    private GeoLocation geoLocation;
    private ArrayList<Station> stations;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public ArrayList<Station> getStations() {
        return stations;
    }

    public void setStations(ArrayList<Station> stations) {
        this.stations = stations;
    }
}
