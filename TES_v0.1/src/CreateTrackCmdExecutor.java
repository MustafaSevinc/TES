public class CreateTrackCmdExecutor extends ExecutorBase {

    private Simulator sim;

    CreateTrackCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.println(String.format("CreateTrackCmdExecutor::execute - %s", cmd.toString()));

        String strId = cmd.getArgs().get("id");
        String strX = cmd.getArgs().get("x");
        String strY = cmd.getArgs().get("y");
        String strZ = cmd.getArgs().get("z");

        if (strId == null || strX == null || strY == null || strZ == null) {
            System.out.printf("give id with value: %s ", cmd.getCommandName());
            return false;
        }

        int id = Integer.parseInt(strId);
        double x = Double.parseDouble(strX);
        double y = Double.parseDouble(strY);
        double z = Double.parseDouble(strZ);

        Track track = new Track(id, x, y, z);

        return sim.addObject(track);
    }
}
