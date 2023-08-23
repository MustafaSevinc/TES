import java.util.Arrays;
import java.util.List;

public class MoveTrackCmdExecutor extends ExecutorBase {

    private Simulator sim;
    private List<String> mustHaveKeys;

    MoveTrackCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
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
        Position position = new Position(x,y,z);
        int speed = Integer.parseInt(strSpeed);

        return sim.moveTrack(id, position, speed);
    }
}
