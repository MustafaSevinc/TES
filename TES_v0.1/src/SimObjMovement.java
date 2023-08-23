import java.util.ArrayList;
import java.util.Arrays;

public class SimObjMovement {

    private Track simObj;
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
        calculateTickChange();
    }

    public boolean moveNextPoint() {
        if(targetPos.size() == 0){
            return false;
        }
        if (Math.abs(simObj.position.x - targetPos.get(0).x) < xPerTick) {
            simObj.position.x = targetPos.get(0).x;
            simObj.position.y = targetPos.get(0).y;
            simObj.position.z = targetPos.get(0).z;
            targetPos.remove(0);
            if(targetPos.size() > 0){
                calculateTickChange();
            }else{
                return false;
            }
        } else {
            simObj.position.x += xPerTick;
            simObj.position.y += yPerTick;
            simObj.position.z += zPerTick;
        }
        return true;
    }

    private void calculateTickChange(){
        this.speedMultByTick = tickInterval * (speed / (Math.sqrt(Math.pow(simObj.position.x - this.targetPos.get(0).x, 2) + Math.pow(simObj.position.y - this.targetPos.get(0).y, 2) + Math.pow(simObj.position.z - this.targetPos.get(0).z, 2)))) / 1000;
        this.xPerTick = (this.targetPos.get(0).x - simObj.position.x) * speedMultByTick;
        this.yPerTick = (this.targetPos.get(0).y - simObj.position.y) * speedMultByTick;
        this.zPerTick = (this.targetPos.get(0).z- simObj.position.z) * speedMultByTick;
    }


    public SimObjectBase getSimObj(){
        return simObj;
    }

    public void addPoint(Position pos){
        targetPos.add(pos);
    }


    public void setTargetPoint(){

    }

}
