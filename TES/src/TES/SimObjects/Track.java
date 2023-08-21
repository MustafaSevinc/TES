package TES.SimObjects;

import TES.SimObjects.Components.Transform;

public class Track extends SimObjectBase {

    private Transform transform;
   // private Id id;
    private double latitude;
    private double longitude;
    private double speed;
    private double altitude;
    private double course;
    private double heading;

    public Track(double posLat, double posLon, double posAlt){
        transform = new Transform(posLat, posLon, posAlt);
    }


    public Transform getTransform() {
        return transform;
    }

    public void setTransform(Transform transform) {
        this.transform = transform;
    }
}
