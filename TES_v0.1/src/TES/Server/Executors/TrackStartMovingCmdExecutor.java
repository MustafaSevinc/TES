package TES.Server.Executors;

import TES.Server.Datas.CommandData;
import TES.Server.Simulator;

public class TrackStartMovingCmdExecutor extends ExecutorBase {

    private Simulator sim;


    public TrackStartMovingCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.println(String.format("TESServer.TrackStartMovingCmdExecutor::execute - %s", cmd.toString()));

        String strId = cmd.getArgs().get("id");

        if(strId == null){
            System.out.printf("give id with value: %s ",cmd.getCommandName());
            return false;
        }

        int id = Integer.parseInt(strId);
        return sim.setTrackMoving(id, true);
      //  return sim.startMotion(id,speed);
    }
}
