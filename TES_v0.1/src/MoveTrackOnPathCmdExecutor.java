import java.util.Arrays;
import java.util.List;

public class MoveTrackOnPathCmdExecutor extends ExecutorBase {

    private Simulator sim;
    private List<String> mustHaveKeys;

    MoveTrackOnPathCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {


        String strId = cmd.getArgs().get("id");
        String strPathId = cmd.getArgs().get("pathId");
        String strSpeed = cmd.getArgs().get("speed");

        if(strId == null || strPathId == null || strSpeed == null){
            System.out.printf("give id with value: %s ",cmd.getCommandName());
            mustHaveKeys.stream().forEach(x-> System.out.printf("%s 0 ",x));
            return false;
        }


        int id = Integer.parseInt(strId);
        int pathId = Integer.parseInt(strPathId);
        double speed = Double.parseDouble(strSpeed);


        return sim.moveOnPath(id, speed, pathId);
    }
}
