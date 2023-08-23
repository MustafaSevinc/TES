public class RemoveTrackCmdExecutor extends ExecutorBase {

    private Simulator sim;

    RemoveTrackCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.println(String.format("RemoveTrackCmdExecutor::execute - %s",cmd.toString()));

        String strId = cmd.getArgs().get("id");

        if(strId == null){
            System.out.printf("give id with value: %s id 0",cmd.getCommandName());
            return false;
        }

        int id = Integer.parseInt(strId);

        sim.removeObject(id);

        return true;
    }
}
