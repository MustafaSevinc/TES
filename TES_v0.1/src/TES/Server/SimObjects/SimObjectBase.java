package TES.Server.SimObjects;

import TES.Server.Datas.GeoPositionData;

public abstract class SimObjectBase {


    public int id;
    public GeoPositionData geoPosition;

    SimObjectBase(int id, GeoPositionData position){
        this.id = id;
        this.geoPosition = position;
    }



}
