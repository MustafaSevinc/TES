package TES.Server.Datas;

public class GeoPositionData {
    public double lat;
    public double lon;
    public double alt;

    public GeoPositionData(Double lon, Double lat, Double alt) {
        this.lon = lon;
        this.lat = lat;
        this.alt = alt;
    }

    public void addPosition(GeoPositionData position){
        this.lon += position.lon;
        this.lat += position.lat;
        this.alt += position.alt;
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
