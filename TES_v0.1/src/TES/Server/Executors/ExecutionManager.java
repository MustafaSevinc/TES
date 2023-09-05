package TES.Server.Executors;
import TES.Server.Datas.CommandData;
import TES.Server.Simulator;

import java.util.HashMap;

public class ExecutionManager {

    Simulator sim;
    public ExecutionManager(Simulator sim){
        this.sim = sim;
        registerExecutors();
    }
    private HashMap<String, ExecutorBase> executors = new HashMap<>();


    /**
     * Simulator için yazılan Executorları register et
     **/
    private void registerExecutors() {
        executors.put("createTrack", new CreateTrackExer(sim));
        executors.put("createPath", new CreatePathExer(sim));
        executors.put("addPathPoint", new AddPointToPathExer(sim));
        executors.put("update", new UpdateSimObjExer(sim));
        executors.put("addPathToTrack", new AddPathToTrackCmdExecutor(sim));
        executors.put("trackStartMoving", new TrackStartMovingExer(sim));

        //exMan.registerExecutor("addTarget", new TESServer.AddTargetPointCmdExecutor(this));
        executors.put("destroy", new RemoveTrackExer(sim));
        executors.put("wait", new WaitCmdExecutor(sim));
        System.out.println("TESServer.Simulator::registerCommands");
    }

    public boolean execute(CommandData cmd) {
        ExecutorBase ex = executors.get(cmd.getCommandName());
        if (ex == null)
            return false;
        return ex.execute(cmd);
    }
}
