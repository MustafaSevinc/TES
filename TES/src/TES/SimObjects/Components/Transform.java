package TES.SimObjects.Components;

public class Transform {

    private Double[] position;

    public Transform(double posLat, double posLon, double posAlt){
        position = new Double[]{posLat,posLon,posAlt};
    }


    public Double[] getPosition() {
        return position;
    }

    public void setPosition(double posLat, double posLon, double posAlt) {
        position[0] = posLat;
        position[1] = posLon;
        position[2] = posAlt;
    }
}
