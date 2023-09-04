package TES.Server;

import TES.Server.SimObjects.SimObjectBase;
import TES.Server.SimObjects.Track;

import java.util.ArrayList;

public class MovementData {

    public SimObjectBase simObj;
    public ArrayList<GeoPosition> geoPositions;
    public int targetPositionIndex;
    public double speed;
    public boolean isMoving;


    public MovementData(SimObjectBase simObj, double speed) {
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
