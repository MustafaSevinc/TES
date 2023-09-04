package TES.Server.SimObjects;

import TES.Server.GeoPosition;

public abstract class SimObjectBase {


    public int id;
    public GeoPosition geoPosition;

    SimObjectBase(int id, GeoPosition position){
        this.id = id;
        this.geoPosition = position;
    }



}
