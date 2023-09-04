package TES.Server.Datas;

import TES.Server.SimObjects.SimObjectBase;

import java.util.ArrayList;

public class MovementData {

    public SimObjectBase simObj;
    public ArrayList<GeoPositionData> geoPositions;
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

    public GeoPositionData getNextPoint(){
        return geoPositions.get(targetPositionIndex);
    }


}
