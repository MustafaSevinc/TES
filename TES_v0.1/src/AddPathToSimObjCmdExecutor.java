import java.util.List;

public class AddPathToSimObjCmdExecutor extends ExecutorBase {

    private Simulator sim;
    private List<String> mustHaveKeys;

    AddPathToSimObjCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {/*


        String strId = cmd.getArgs().get("id");
        String strPathId = cmd.getArgs().get("pathId");

        if(strId == null || strPathId == null){
            System.out.printf("give id with value: %s ",cmd.getCommandName());
            mustHaveKeys.stream().forEach(x-> System.out.printf("%s 0 ",x));
            return false;
        }


        int id = Integer.parseInt(strId);
        int pathId = Integer.parseInt(strPathId);


        return sim.moveOnPath(id, pathId);*/
        return true;
    }
}
