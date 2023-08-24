public class UpdateTrackCmdExecutor extends ExecutorBase {

    private Simulator sim;

    UpdateTrackCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.printf("CreatePathCmdExecutor::execute - %s%n", cmd.toString());


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



        double lon = (strLon == null) ? null : Double.parseDouble(strLon);
        double lat = (strLon == null) ? null : Double.parseDouble(strLat);
        double alt = (strLon == null) ? null : Double.parseDouble(strAlt);
        double speed = (strLon == null) ? null : Double.parseDouble(strSpeed);


        return sim.updateTrack(id, new GeoPosition(lon,lat,alt), speed);
    }
}
