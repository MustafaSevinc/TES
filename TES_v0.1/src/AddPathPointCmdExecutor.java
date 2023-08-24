import java.util.List;

public class AddPathPointCmdExecutor extends ExecutorBase {

    private Simulator sim;

    AddPathPointCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.println(String.format("MoveTrackCmdTrackExecutor::execute - %s", cmd.toString()));

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

        return sim.addPointToPath(id, new GeoPosition(lon, lat, alt));
    }
}
