import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Simulator {

    private ExecutionManager exMan;
    private HashMap<Integer, SimObjectBase> simObjects;
    private ConcurrentLinkedQueue<List> updatingList;
    private short tickInterval = 500;


    private Timer updateTimer;


    public Simulator() {
        simObjects = new HashMap<>();
        exMan = new ExecutionManager();
        updatingList = new ConcurrentLinkedQueue<>();

        //configureUpdateTimer();

        updateTimer = new Timer();
        TimerTask update = new TimerTask() {
            @Override
            public void run() {
                    for(List pair : updatingList){
                        System.out.println("Runnable enteredd");

                        Track track = (Track) pair.get(0);
                        double targetLat = (double) pair.get(1);
                        double speed =  (double) pair.get(2);
                        double meterps = speed*1000/tickInterval;
                        if(Math.abs(track.latitude - targetLat)<meterps){
                            track.latitude = targetLat;
                            updatingList.remove(pair);
                        }else{
                            track.latitude += meterps;
                        }
                        if(sendSimObjData(track)){
                            System.out.println("Data Send");
                        }
                    }
                }
        };
        updateTimer.scheduleAtFixedRate(update, 0, tickInterval);
        registerCommands();
    }

    private void registerCommands() {
        exMan.registerExecutor("addTrack", new CreateTrackCmdExecutor(this));
        exMan.registerExecutor("moveTrack",new MoveTrackCmdExecutor(this));
        System.out.println("Simulator::registerCommands");
    }

    public void close() {
        simObjects = null;
    }

    public void execute(CommandData cmd) {
        if (exMan.execute(cmd))
            System.out.println("Simulator::execute - execution completed");
    }

    public void addObject(SimObjectBase simObject) {
        simObjects.put(simObject.id, simObject);
    }

    public void removeObject(int Id) {
        simObjects.remove(Id);
    }


    public void moveTrack(int Id, double targetLat, double speed) {
        updatingList.add(Arrays.asList(simObjects.get(Id), targetLat, speed));
        System.out.println("trying to move obj");
    }

    private boolean sendSimObjData(Track track){
        HashMap trackData = new HashMap<>();
        trackData.put("lat",track.latitude);
        trackData.put("lon",track.longitude);
        trackData.put("alt",track.altitude);
        trackData.put("id",track.id);
        CommandData cmd = new CommandData("UpdateView",trackData);
        System.out.println("Comamnd Send:");
        System.out.println(cmd.toString());
        return true;
    }


    public void printSimObjectNames() {
        simObjects.forEach((key, value) -> {
            System.out.println(key.toString()+value.toString());
        });
    }

}
