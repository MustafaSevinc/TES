package TES.Server;

public class GeoPosition {
    public double lat;
    public double lon;
    public double alt;

    public GeoPosition(Double lon, Double lat, Double alt) {
        this.lon = lon;
        this.lat = lat;
        this.alt = alt;
    }

    @Override
    public String toString() {
        return "TESServer.GeoPosition{" +
                "lon=" + lon +
                ", lat=" + lat +
                ", alt=" + alt +
                '}';
    }
}
