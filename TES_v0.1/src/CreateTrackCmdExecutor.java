public class CreateTrackCmdExecutor extends ExecutorBase {

    private Simulator sim;

    CreateTrackCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.printf("CreateTrackCmdExecutor::execute - %s%n", cmd.toString());

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
        Track track = new Track(id, new GeoPosition(lon,lat,alt));

        return sim.addObject(track);
    }
}
