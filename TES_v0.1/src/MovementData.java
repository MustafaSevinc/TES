import java.util.ArrayList;

public class MovementData {

    public Track simObj;
    public ArrayList<GeoPosition> geoPositions;
    public int targetPositionIndex;
    public double speed;
    public boolean isMoving;


    public MovementData(Track simObj, double speed) {
        this.simObj = simObj;
        this.geoPositions = new ArrayList<>();
        targetPositionIndex = 0;
        this.speed = speed;
        this.isMoving = false;
    }

    public GeoPosition getNextPoint(){
        return geoPositions.get(targetPositionIndex);
    }


}
