public class Track extends SimObjectBase {

    public Track(int id, GeoPosition geoPosition){
        super(id,geoPosition);
    }


    public String toString(){
        return "Track id:"+id+"\n Pozisyon: "+ super.geoPosition.lat +" "+ geoPosition.lon+" "+ geoPosition.alt;
    }


}
