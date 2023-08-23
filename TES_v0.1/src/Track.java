public class Track extends SimObjectBase {

    public GeoCoordinate geoCoordinate;
    public double speed;
    public double course;
    public double heading;

    public Track(int id, double lat, double lon, double alt){
        super(id);
        geoCoordinate = new GeoCoordinate(lat,alt,lon);
    }

    public String toString(){
        return "Track id:"+id+"\n Pozisyon: "+ geoCoordinate.x +" "+ geoCoordinate.y+" "+ geoCoordinate.z;
    }


}
