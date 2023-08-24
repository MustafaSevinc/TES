public class GeoPosition {
    public double lat;
    public double lon;
    public double alt;

    public GeoPosition(double lon, double lat, double alt) {
        this.lon = lon;
        this.lat = lat;
        this.alt = alt;
    }

    @Override
    public String toString() {
        return "GeoPosition{" +
                "lon=" + lon +
                ", lat=" + lat +
                ", alt=" + alt +
                '}';
    }
}
