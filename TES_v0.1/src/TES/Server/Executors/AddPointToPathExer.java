package TES.Server.Executors;

import TES.Server.Datas.CommandData;
import TES.Server.Datas.GeoPositionData;
import TES.Server.Simulator;

public class AddPointToPathExer extends ExecutorBase {

    private Simulator sim;

    public AddPointToPathExer(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.println(String.format("TESServer.AddPathPointCmdExecutor::execute - %s", cmd.toString()));

        String strId = cmd.getArgs().get("id");
        String strLon = cmd.getArgs().get("lon");
        String strLat = cmd.getArgs().get("lat");
        String strAlt = cmd.getArgs().get("alt");


        if (strId == null) {
            System.out.printf("give id with value: %s ", cmd.getCommandName());
            return false;
        }

        int id = Integer.parseInt(strId);
        double lon = (strLon == null) ? 0 : Double.parseDouble(strLon);
        double lat = (strLat == null) ? 0 : Double.parseDouble(strLat);
        double alt = (strAlt == null) ? 0 : Double.parseDouble(strAlt);

        return sim.addPointToPath(id, new GeoPositionData(lon, lat, alt));
    }
}
