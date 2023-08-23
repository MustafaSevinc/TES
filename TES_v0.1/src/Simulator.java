import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulator {

    private ExecutionManager exMan;
    private HashMap<Integer, SimObjectBase> simObjects;
    private ConcurrentLinkedQueue<SimObjMovement> updatingList;
    private long tickInterval;
    private Timer updateTimer;


    public Simulator(long updateDelay, long updateTickIntervalMS) {
        simObjects = new HashMap<>();
        exMan = new ExecutionManager();
        updatingList = new ConcurrentLinkedQueue<>();
        updateTimer = new Timer();
        tickInterval = updateTickIntervalMS;
        configureUpdateTimer(updateDelay, updateTickIntervalMS);
    }

    private void configureUpdateTimer(long delay, long tickInterval) {
        TimerTask update = new TimerTask() {
            @Override
            public void run() {
                for (SimObjMovement simObjMovement : updatingList) {
                    if(simObjects.get(simObjMovement.getSimObj().id) == null || !simObjMovement.moveNextPoint()){
                            updatingList.remove(simObjMovement);
                    }
                    sendSimObjData((Track) simObjMovement.getSimObj());
                }
            }
        };
        updateTimer.scheduleAtFixedRate(update, delay, tickInterval);
        registerCommands();
    }


    private void registerCommands() {
        exMan.registerExecutor("createTrack", new CreateTrackCmdExecutor(this));
        exMan.registerExecutor("moveTrack", new MoveTrackCmdExecutor(this));
        exMan.registerExecutor("removeTrack", new RemoveTrackCmdExecutor(this));
        exMan.registerExecutor("wait", new WaitCmdExecutor(this));
        exMan.registerExecutor("createPath", new CreatePathCmdExecutor(this));
        exMan.registerExecutor("addPathPoint", new AddPathPointCmdExecutor(this));
        exMan.registerExecutor("moveTrackOnPath", new MoveTrackOnPathCmdExecutor(this));

        System.out.println("Simulator::registerCommands");
    }

    public void close() {
        updateTimer.cancel();
    }

    public void execute(CommandData cmd) {
        if (exMan.execute(cmd))
            System.out.println("Simulator::execute - execution completed");
    }

    public boolean addObject(SimObjectBase simObject) {
        if(simObjects.get(simObject.id) != null){
            System.out.printf("Simulator::addObject FAIL. SimObj with id % already exists.",simObject.id);
            return false;
        }
        simObjects.put(simObject.id, simObject);
        return true;
    }

    public void removeObject(int id) {
        simObjects.remove(id);
    }

    public boolean addPointToPath(int pathId, Position pos){
        Path path = (Path) simObjects.get(pathId);
        if(path== null){
            System.out.printf("Path with id % coulden't find",pathId);
            return false;
        }
        path.addPoint(pos);
        return true;
    }



    public boolean moveTrack(int id, double speed, Position... targetPos) {
        if(simObjects.get(id) == null){
            System.out.printf("Simulator::moveTrack FAIL. SimObj with id % does not exists.",id);
            return false;
        }
        updatingList.removeIf(obj -> obj.getSimObj().id == id);
        SimObjMovement trackMovement = new SimObjMovement((Track) simObjects.get(id), targetPos, speed, tickInterval);
        updatingList.add(trackMovement);
        System.out.println("Simulator::moveTrack");
        return true;
    }

    public boolean moveOnPath(int id, double speed, int pathId){
        Path path = (Path) simObjects.get(pathId);
        return moveTrack(id,speed, path.getPointsAsArray());

    }



    private boolean sendSimObjData(Track track) {
        HashMap trackData = new HashMap<>();
        trackData.put("x", track.position.x);
        trackData.put("y", track.position.y);
        trackData.put("z", track.position.z);
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

}
