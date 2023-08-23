public class Track extends SimObjectBase {

    public Position position;
    public double speed;
    public double course;
    public double heading;

    public Track(int id, double lat, double lon, double alt){
        super(id);
        position = new Position(lat,alt,lon);
    }

    public String toString(){
        return "Track id:"+id+"\n Pozisyon: "+ position.x +" "+position.y+" "+position.z;
    }


}
