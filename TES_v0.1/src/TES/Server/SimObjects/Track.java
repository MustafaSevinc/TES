package TES.Server.SimObjects;

import TES.Server.GeoPosition;

public class Track extends SimObjectBase {

    public Track(int id, GeoPosition geoPosition){
        super(id,geoPosition);
    }


    public String toString(){
        return "TESServer.Track id:"+id+"\n Pozisyon: "+ super.geoPosition.lat +" "+ geoPosition.lon+" "+ geoPosition.alt;
    }


}
