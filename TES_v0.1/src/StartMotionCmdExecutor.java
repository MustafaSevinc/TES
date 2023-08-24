import java.util.Arrays;
import java.util.List;

public class StartMotionCmdExecutor extends ExecutorBase {

    private Simulator sim;


    StartMotionCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {

        String strId = cmd.getArgs().get("id");
        String strSpeed = cmd.getArgs().get("speed");

        if(strId == null || strSpeed == null){
            System.out.printf("give id with value: %s ",cmd.getCommandName());
            return false;
        }

        int id = Integer.parseInt(strId);
        int speed = Integer.parseInt(strSpeed);

        return sim.startMotion(id,speed);
    }
}
