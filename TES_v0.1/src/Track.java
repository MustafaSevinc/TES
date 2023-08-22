public class Track extends SimObjectBase {
    public double latitude;
    public double longitude;
    public double speed;
    public double altitude;
    public double course;
    public double heading;

    public Track(int id, double lat, double lon, double alt){
        super(id);
        this.latitude = lat;
        this.longitude = lon;
        this.altitude = alt;
    }

    public String toString(){
        return "Track id:"+id+"\n Pozisyon: "+ latitude +" "+longitude+" "+altitude;
    }


}
