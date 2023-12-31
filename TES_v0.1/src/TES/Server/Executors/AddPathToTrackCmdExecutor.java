package TES.Server.Executors;

import TES.Server.Datas.CommandData;
import TES.Server.Simulator;

public class AddPathToTrackCmdExecutor extends ExecutorBase {

    private Simulator sim;

    public AddPathToTrackCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.println(String.format("TESServer.AddPathToTrackCmdExecutor::execute - %s", cmd.toString()));


        String strId = cmd.getArgs().get("trackId");
        String strPathId = cmd.getArgs().get("pathId");

        if(strId == null || strPathId == null){
            System.out.printf("give id with value: %s ",cmd.getCommandName());
            return false;
        }


        int trackId = Integer.parseInt(strId);
        int pathId = Integer.parseInt(strPathId);
        return sim.addPathToTrack(trackId, pathId);
    }
}
