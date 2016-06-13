package findgas.com.findgas.station;

/**
 * Created by akshaymanathkar on 31/05/16.
 */
public class GeoLocation {
    private String countryShort;
    private double lat;
    private double lng;
    private String countryLong;
    private String regionShort;
    private String regionLong;
    private String cityLong;
    private String address;

    public String getCountryShort() {

        return countryShort;
    }

    public void setCountryShort(String countryShort) {
        this.countryShort = countryShort;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCountryLong() {
        return countryLong;
    }

    public void setCountryLong(String countryLong) {
        this.countryLong = countryLong;
    }

    public String getRegionShort() {
        return regionShort;
    }

    public void setRegionShort(String regionShort) {
        this.regionShort = regionShort;
    }

    public String getRegionLong() {
        return regionLong;
    }

    public void setRegionLong(String regionLong) {
        this.regionLong = regionLong;
    }

    public String getCityLong() {
        return cityLong;
    }

    public void setCityLong(String cityLong) {
        this.cityLong = cityLong;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
