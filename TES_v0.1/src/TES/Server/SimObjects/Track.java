package TES.Server.SimObjects;

import TES.Server.Datas.GeoPositionData;

public class Track extends SimObjectBase {

    public Track(int id, GeoPositionData geoPosition){
        super(id,geoPosition);
    }


    public String toString(){
        return "TESServer.Track id:"+id+"\n Pozisyon: "+ super.geoPosition.lat +" "+ geoPosition.lon+" "+ geoPosition.alt;
    }


}
