public class MoveTrackCmdExecutor extends ExecutorBase {

    private Simulator sim;

    MoveTrackCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.println(String.format("CreateTrackExecutor::execute - %s",cmd.toString()));

        String strId = cmd.getArgs().get("id");
        String strLat = cmd.getArgs().get("lat");
        String strSpeed = cmd.getArgs().get("speed");



        if(strId == null){
            System.out.printf("give id with value: %s id 0",cmd.getCommandName());
            return false;
        }

        int id = Integer.parseInt(strId);
        int lat = Integer.parseInt(strLat);
        int speed = Integer.parseInt(strSpeed);


        sim.moveTrack(id,lat,speed);


        return true;
    }
}
