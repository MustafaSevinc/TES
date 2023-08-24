import java.net.Socket;
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

        //exMan.registerExecutor("addTarget", new AddTargetPointCmdExecutor(this));
        exMan.registerExecutor("destroyTrack", new RemoveTrackCmdExecutor(this));
        exMan.registerExecutor("wait", new WaitCmdExecutor(this));
        exMan.registerExecutor("addPathToSimObj", new AddPathToSimObjCmdExecutor(this));
        exMan.registerExecutor("startMotion", new StartMotionCmdExecutor(this));
        System.out.println("Simulator::registerCommands");
    }

    private void configureUpdateTimer(long delay, long tickInterval) {
        TimerTask update = new TimerTask() {
            @Override
            public void run() {
                for (MovementData movObj : updatingList) {
                    if (movObj.isMoving) {
                        System.out.println(movObj.simObj.geoPosition);




                        movObj.simObj.geoPosition = calcCoordAfterTime(movObj, (double) tickInterval/1000);
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

    public boolean updateTrack(int id, GeoPosition pos, double speed){
        if (simObjects.get(id) instanceof Track track) {
            track.geoPosition.alt = pos.alt == null ? track.geoPosition.alt : pos.alt;
            return true;
        }
        System.out.println("Simulator::updateTrack-> Track doesn't exist");
        return false;
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
    private GeoPosition calcCoordAfterTime(MovementData kinematic, double dtSec) {
        ArrayList<GeoPosition> targetCoords = kinematic.geoPositions;
        double distToMove = kinematic.speed * dtSec;
        double temp_dist = 0;
        GeoPosition temp_coord = kinematic.simObj.geoPosition;
        // sonraki pozisyona göre bilmemne
        for (GeoPosition tar_coord : targetCoords) {
            temp_dist = distance(temp_coord, tar_coord);
            if (temp_dist < distToMove) {
                distToMove -= temp_dist;
                temp_coord = tar_coord;
                continue;
            }
            double distRatio = distToMove / temp_dist;
            GeoPosition geoPosition = new GeoPosition(temp_coord.lat + (tar_coord.lat - temp_coord.lat) * distRatio,
                    temp_coord.lon + (tar_coord.lon - temp_coord.lon) * distRatio,
                    temp_coord.alt + (tar_coord.alt - temp_coord.alt) * distRatio);
            return geoPosition;//Eğer uçak yamuk giderse buranın suçu;
        }

        return targetCoords.get(targetCoords.size() - 1);
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
