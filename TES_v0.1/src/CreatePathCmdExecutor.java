public class CreatePathCmdExecutor extends ExecutorBase {

    private Simulator sim;

    CreatePathCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.println(String.format("CreatePathCmdExecutor::execute - %s", cmd.toString()));

        String strId = cmd.getArgs().get("id");

        if (strId == null) {
            System.out.printf("give id with value: %s ", cmd.getCommandName());
            return false;
        }

        int id = Integer.parseInt(strId);

        Path path = new Path(id);

        return sim.addObject(path);
    }
}
