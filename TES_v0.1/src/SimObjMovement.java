import java.util.ArrayList;
import java.util.Arrays;

public class SimObjMovement {

    public Track simObj;
    private ArrayList<Position> targetPos;
    private double speed;
    private double tickInterval;
    private double speedMultByTick;
    private double xPerTick;
    private double yPerTick;
    private double zPerTick;


    public SimObjMovement(Track simObj, Position[] targetPos, double speed, double tickInterval) {
        this.simObj = simObj;
        this.targetPos = new ArrayList<>();
        this.targetPos.addAll(Arrays.asList(targetPos));
        this.speed = speed;
        this.tickInterval = tickInterval;
        calcDeltaPos(tickInterval);
    }

    public Position calcDeltaPos(double deltaTime){
        this.speedMultByTick = deltaTime * (speed /
                (Math.sqrt(
                        Math.pow(simObj.position.x - this.targetPos.get(0).x, 2)
                        + Math.pow(simObj.position.y - this.targetPos.get(0).y, 2)
                        + Math.pow(simObj.position.z - this.targetPos.get(0).z, 2)))) / 1000;
        this.xPerTick = (this.targetPos.get(0).x - simObj.position.x) * speedMultByTick;
        this.yPerTick = (this.targetPos.get(0).y - simObj.position.y) * speedMultByTick;
        this.zPerTick = (this.targetPos.get(0).z - simObj.position.z) * speedMultByTick;
        Position pos = new Position(xPerTick,yPerTick,zPerTick);
        return pos;
    }
    public boolean isArrivedPos(){
    return Math.abs(simObj.position.x - this.targetPos.get(0).x)<xPerTick;
    }

    public SimObjectBase getSimObj(){
        return this.simObj;
    }

    public void addPoint(Position pos){
        targetPos.add(pos);
    }

    public void dequeuePoint(){
        targetPos.remove(0);
    }

    public Position getTargetPoint(){
        if(targetPos.size() == 0)
            return null;

        return targetPos.get(0);
    }

    public void setTargetPoint(){

    }

}
