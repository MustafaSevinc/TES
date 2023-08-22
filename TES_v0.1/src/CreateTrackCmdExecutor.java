public class CreateTrackCmdExecutor extends ExecutorBase {

    private Simulator sim;

    CreateTrackCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.println(String.format("CreateTrackExecutor::execute - %s",cmd.toString()));

        String strId = cmd.getArgs().get("id");

        if(strId == null){
            System.out.printf("give id with value: %s id 0",cmd.getCommandName());
            return false;
        }

        int id = Integer.parseInt(strId);

        Track track = new Track(id,0,0,0);
        sim.addObject(track);


        return true;
    }
}
