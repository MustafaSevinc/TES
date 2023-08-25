import java.util.List;

public class AddTargetPointCmdExecutor extends ExecutorBase {

    private Simulator sim;
    private List<String> mustHaveKeys;

    AddTargetPointCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {/*
        System.out.println(String.format("MoveTrackCmdTrackExecutor::execute - %s",cmd.toString()));
        mustHaveKeys = Arrays.asList("id","x","y","z","speed");


        String strId = cmd.getArgs().get("id");
        String strX = cmd.getArgs().get("x");
        String strY = cmd.getArgs().get("y");
        String strZ = cmd.getArgs().get("z");
        String strSpeed = cmd.getArgs().get("speed");

        if(strId == null || strX == null || strY == null || strZ == null || strSpeed == null){
            System.out.printf("give id with value: %s ",cmd.getCommandName());
            mustHaveKeys.stream().forEach(x-> System.out.printf("%s 0 ",x));
            return false;
        }

        int id = Integer.parseInt(strId);
        double x = Double.parseDouble(strX);
        double y = Double.parseDouble(strY);
        double z = Double.parseDouble(strZ);
        GeoPosition geoPosition = new GeoPosition(x,y,z);
        int speed = Integer.parseInt(strSpeed);

        return sim.moveTrack(id, speed, geoPosition);*/

        String strId = cmd.getArgs().get("id");
        String strLon = cmd.getArgs().get("lon");
        String strLat = cmd.getArgs().get("lat");
        String strAlt = cmd.getArgs().get("alt");

        if (strId == null || strLon == null || strLat == null) {
            System.out.printf("give id with value: %s ", cmd.getCommandName());
            return false;
        }else if(strAlt == null){
            strAlt = "0";
        }

        int id = Integer.parseInt(strId);
        double lon = Double.parseDouble(strLon);
        double lat = Double.parseDouble(strLat);
        double alt = Double.parseDouble(strAlt);
        return false;
       //return sim.addTargetPoint(id,new GeoPosition(lon,lat,alt));
    }
}
