import java.util.ArrayList;

public class Simulator {

    private ExecutionManager exMan;
    private ArrayList<SimObjectBase> simObjects;

    public Simulator() {
        simObjects = new ArrayList<>();
        exMan = new ExecutionManager();
        registerCommands();
    }

    private void registerCommands() {
        System.out.println("Simulator::registerCommands");
        exMan.registerExecutor("addTrack", new CreateTrackCmdExecutor(this));
    }

    public void close() {
        simObjects = null;
    }

    public void execute(CommandData cmd) {
        if (exMan.execute(cmd))
            System.out.println("Simulator::execute - execution completed");
    }

    public void addObject(SimObjectBase simObject) {
        simObjects.add(simObject);
    }

    public void removeObject(int Id) {
        SimObjectBase tempSimObj = null;
        for (SimObjectBase simObj : simObjects) {
            if (simObj.id == Id) {
                tempSimObj = simObj;
                break;
            }
        }
        simObjects.remove(tempSimObj);
    }


    public void printSimObjectNames() {
        for (SimObjectBase simObj : simObjects) {
            System.out.println(simObj.toString());
        }
    }

}
