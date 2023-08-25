import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulator {

    private ExecutionManager exMan;
    private HashMap<Integer, SimObjectBase> simObjects;
    private ConcurrentLinkedQueue<MovementData> updatingList;
    private Timer updateTimer;


    public Simulator(long updateDelay, long updateTickIntervalMS) {
        simObjects = new HashMap<>();
        exMan = new ExecutionManager();
        updatingList = new ConcurrentLinkedQueue<>();
        updateTimer = new Timer();
        configureUpdateTimer(updateDelay, updateTickIntervalMS);
        registerCommands();
    }

    private void registerCommands() {
        exMan.registerExecutor("createTrack", new CreateTrackCmdExecutor(this));
        exMan.registerExecutor("createPath", new CreatePathCmdExecutor(this));
        exMan.registerExecutor("addPathPoint", new AddPathPointCmdExecutor(this));
        exMan.registerExecutor("update", new UpdateSimObjCmdExecutor(this));
        exMan.registerExecutor("addPathToTrack", new AddPathToTrackCmdExecutor(this));
        exMan.registerExecutor("trackStartMoving", new TrackStartMovingCmdExecutor(this));


        //exMan.registerExecutor("addTarget", new AddTargetPointCmdExecutor(this));
        exMan.registerExecutor("destroy", new RemoveTrackCmdExecutor(this));
        exMan.registerExecutor("wait", new WaitCmdExecutor(this));
        System.out.println("Simulator::registerCommands");
    }

    private void configureUpdateTimer(long delay, long tickInterval) {
        TimerTask update = new TimerTask() {
            @Override
            public void run() {
                for (MovementData movObj : updatingList) {
                    if (movObj.isMoving) {
                        System.out.println(movObj.simObj.geoPosition);

                        movObj.simObj.geoPosition = calcCoordAfterTime(movObj, (double) tickInterval / 1000);
                    }
                }
            }
        };
        updateTimer.scheduleAtFixedRate(update, delay, tickInterval);
    }

    public void close() {
        updateTimer.cancel();
    }

    public void execute(CommandData cmd) {
        if (exMan.execute(cmd))
            System.out.println("Simulator::execute - execution completed");
    }

    public boolean addObject(SimObjectBase simObject) {
        if (simObjects.get(simObject.id) != null) {
            System.out.printf("Simulator::addObject FAIL. SimObj with id %s already exists.", simObject.id);
            return false;
        }
        simObjects.put(simObject.id, simObject);
        return true;
    }

    public void removeObject(int id) {
        updatingList.stream().filter(movData -> movData.simObj.id == id).findAny().ifPresent(mov -> updatingList.remove(mov));
        simObjects.remove(id);
    }

    public boolean addPointToPath(int pathId, GeoPosition pos) {
        if (!(simObjects.get(pathId) instanceof Path path)) {
            System.out.printf("Path with id %s coulden't find", pathId);
            return false;
        }
        path.addPoint(pos);
        return true;
    }

    public boolean addPathToTrack(int trackId, int pathId) {
        MovementData mov = getMovementData(trackId);
        if (!(simObjects.get(pathId) instanceof Path path)) {
            System.out.printf("Path with id %s coulden't find", pathId);
            System.out.println(simObjects);
            return false;
        }
        mov.geoPositions.addAll(path.points);
        return true;
    }

/*
    //Sim Obje Vermek Bertan Abinin Fikriydi!
        private SimObjectBase getSimObject(int id) {
        return simObjects.get(id);
    }*/

    public boolean setSpeedOfObj(int id, double speed) {
        MovementData mov = getMovementData(id);
        mov.speed = speed;
        return true;
    }

    public boolean setPosOfObj(int id, GeoPosition pos) {
        SimObjectBase simObj = simObjects.get(id);
        if (!Double.isNaN(pos.lon)) {
            simObj.geoPosition.lon = pos.lon;
        }
        if (!Double.isNaN(pos.lat)) {
            simObj.geoPosition.lat = pos.lat;
        }
        if (!Double.isNaN(pos.alt)) {
            simObj.geoPosition.alt = pos.alt;
        }
        return true;
    }

    public boolean setTrackMoving(int id, boolean isMoving) {
        getMovementData(id).isMoving = isMoving;
        return true;
    }


    private MovementData getMovementData(int trackId) {
        MovementData mov = updatingList.stream().filter(movData -> movData.simObj.id == trackId).findAny().orElse(null);
        if (mov == null) {
            mov = new MovementData((Track) simObjects.get(trackId), 0);
            updatingList.add(mov);
        }
        return mov;
    }


  /*  public boolean addTargetPoint(int simObjId, GeoPosition geoPosition) {
        for (MovementData kinematic : updatingList) {
            if (kinematic.simObj.id == simObjId) {
                kinematic.geoPositions.add(geoPosition);
            }
        }
        MovementData trackMovement = new MovementData((Track) simObjects.get(simObjId), 0);
        updatingList.add(trackMovement);

        System.out.println("Simulator::addTargetCoord");
        return true;
    }

    public boolean startMotion(int simObjId, double speed) {
        for (MovementData kinematic : updatingList) {
            if (kinematic.simObj.id == simObjId) {
                kinematic.speed = speed;
                kinematic.isMoving = true;
                return true;
            }
        }
        return false;
    }
*/
/*
    public boolean moveOnPath(int id, int pathId) {
        SimObjectBase obj = simObjects.get(pathId);
        if (!(obj instanceof Path path)) {
            // todo log error
            return false;
        }
        for (GeoPosition p : path.points){
            addTargetPoint(id,p);
        }
    }
*/

    private boolean sendSimObjData(Track track) {
        HashMap trackData = new HashMap<>();
        trackData.put("x", track.geoPosition.lon);
        trackData.put("y", track.geoPosition.lat);
        trackData.put("z", track.geoPosition.alt);
        trackData.put("id", track.id);
        CommandData cmd = new CommandData("UpdateView", trackData);
        System.out.println("Comamnd Send:");
        System.out.println(cmd.toString());
        return true;
    }


    public void printSimObjectNames() {
        simObjects.forEach((key, value) -> {
            System.out.println(key.toString() + value.toString());
        });
    }


    // TODO Mustafa method basına 20 comment line

    /********************************* GEO CALCULATIONS **************************************/
    private GeoPosition calcCoordAfterTime(MovementData mov, double dtSec) {
        double distToMove = mov.speed * dtSec;
        GeoPosition currentPos = mov.simObj.geoPosition;
        GeoPosition nextPos;
        for (int i = mov.targetPositionIndex; i < mov.geoPositions.size(); i++) {
            nextPos = mov.getNextPoint();
            double temp_dist = distance(currentPos, nextPos);
            if (distToMove > temp_dist) {
                currentPos = nextPos;
                mov.targetPositionIndex += 1;
                distToMove -= temp_dist;
                continue;
            }
            double distRatio = distToMove/temp_dist;
            return new GeoPosition(
                    currentPos.lon+(nextPos.lon-currentPos.lon)*distRatio,
                    currentPos.lat+(nextPos.lat- currentPos.lat)*distRatio,
                    currentPos.alt+(nextPos.alt-currentPos.alt)*distRatio);

        }
        mov.targetPositionIndex -= 1;
        mov.isMoving= false;
        return mov.getNextPoint();

    }

    public double distance(GeoPosition pos1, GeoPosition pos2) {
        final int R = 6371; // Earth is totaly circle
        double latDistance = Math.toRadians(pos2.lat - pos1.lat);
        double lonDistance = Math.toRadians(pos2.lon - pos1.lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(pos1.lat)) * Math.cos(Math.toRadians(pos2.lat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        // whf is c?
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // Metre cinsinden
        double height = pos1.alt - pos2.alt;
        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        return Math.sqrt(distance);
    }
}
