package TES.Server.Executors;

import TES.Server.Datas.CommandData;
import TES.Server.Datas.GeoPositionData;
import TES.Server.Simulator;
import TES.Server.SimObjects.Track;

public class CreateTrackExer extends ExecutorBase {

    private Simulator sim;

    public CreateTrackExer(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.printf("TESServer.CreateTrackCmdExecutor::execute - %s%n", cmd.toString());

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
        double lat = (strLon == null) ? 0 : Double.parseDouble(strLat);
        double alt = (strLon == null) ? 0 : Double.parseDouble(strAlt);
        Track track = new Track(id, new GeoPositionData(lon,lat,alt));
        return sim.addObject(track);
    }
}
