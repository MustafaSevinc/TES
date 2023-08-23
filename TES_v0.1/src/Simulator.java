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
                    if(simObjects.get(simObjMovement.getSimObj().id) == null || simObjMovement.isArrived()){
                            updatingList.remove(simObjMovement);
                            continue;
                    }
                    simObjMovement.move();
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
        System.out.println("Simulator::registerCommands");
    }

    public void close() {
    }

    public void execute(CommandData cmd) {
        if (exMan.execute(cmd))
            System.out.println("Simulator::execute - execution completed");
    }

    public void addObject(SimObjectBase simObject) {
        simObjects.put(simObject.id, simObject);
    }

    public void removeObject(int id) {
        simObjects.remove(id);
    }


    public void moveTrack(int id, Position targetpPos, double speed) {
        SimObjMovement trackMovement = new SimObjMovement((Track) simObjects.get(id), targetpPos, speed, tickInterval);
        updatingList.add(trackMovement);
        System.out.println("Simulator::moveTrack");
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
