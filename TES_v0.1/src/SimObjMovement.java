public class SimObjMovement {

    private Track simObj;
    private Position targetPos;
    private double speed;
    private double tickInterval;
    private double speedMultByTick;
    private double xPerTick;
    private double yPerTick;
    private double zPerTick;


    public SimObjMovement(Track simObj, Position targetPos, double speed, double tickInterval) {
        this.simObj = simObj;
        this.targetPos = targetPos;
        this.speed = speed;
        this.tickInterval = tickInterval;
        this.speedMultByTick = tickInterval * (speed / (Math.sqrt(Math.pow(simObj.position.x - targetPos.x, 2) + Math.pow(simObj.position.y - targetPos.y, 2) + Math.pow(simObj.position.z - targetPos.z, 2)))) / 1000;
        this.xPerTick = (targetPos.x - simObj.position.x) * speedMultByTick;
        this.yPerTick = (targetPos.y - simObj.position.y) * speedMultByTick;
        this.zPerTick = (targetPos.z - simObj.position.z) * speedMultByTick;
    }

    public void move() {
        if (Math.abs(simObj.position.x - targetPos.x) < xPerTick) {
            simObj.position.x = targetPos.x;
            simObj.position.y = targetPos.y;
            simObj.position.z = targetPos.z;
        } else {
            simObj.position.x += xPerTick;
            simObj.position.y += yPerTick;
            simObj.position.z += zPerTick;
        }
    }

    public boolean isArrived() {
        return simObj.position.x == targetPos.x && simObj.position.y == targetPos.y && simObj.position.z == targetPos.z;
    }


    public SimObjectBase getSimObj(){
        return simObj;
    }

}
