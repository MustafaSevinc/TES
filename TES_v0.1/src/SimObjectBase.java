public abstract class SimObjectBase {


    int id;
    GeoPosition geoPosition;

    SimObjectBase(int id, GeoPosition position){
        this.id = id;
        this.geoPosition = position;
    }



}
