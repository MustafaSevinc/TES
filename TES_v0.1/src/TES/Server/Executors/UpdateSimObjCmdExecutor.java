package TES.Server.Executors;

import TES.Server.CommandData;
import TES.Server.GeoPosition;
import TES.Server.Simulator;

public class UpdateSimObjCmdExecutor extends ExecutorBase {

    private Simulator sim;

    public UpdateSimObjCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.printf("UpdateTrackCmdExecutor::execute - %s%n", cmd.toString());


        String strId = cmd.getArgs().get("id");
        String strLon = cmd.getArgs().get("lon");
        String strLat = cmd.getArgs().get("lat");
        String strAlt = cmd.getArgs().get("alt");
        String strSpeed = cmd.getArgs().get("speed");


        if (strId == null) {
            System.out.printf("give id with value: %s ", cmd.getCommandName());
            return false;
        }

        int id = Integer.parseInt(strId);

        if (strSpeed != null) {
            System.out.println(strSpeed);
            sim.setSpeedOfObj(id, Double.parseDouble(strSpeed));
        }

        //Eğer inputta olmayan değerler varsa null set ediyoruz.
        GeoPosition pos = new GeoPosition(
                ((strLon == null) ? Double.NaN : Double.parseDouble(strLon)),
                ((strLat == null) ? Double.NaN : Double.parseDouble(strLat)),
                ((strAlt == null) ? Double.NaN : Double.parseDouble(strAlt)));

        sim.setPosOfObj(id, pos);

        return true;
    }
}
