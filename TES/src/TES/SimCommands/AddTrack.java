package TES.SimCommands;

import TES.SimObjects.*;
import TES.Simulator;

import java.util.List;

public class AddTrack extends ExecuteBase {

    public AddTrack(){
        super("addtrack");
    }


    @Override
    public void execute(Simulator sim) {
        // create sim object
        Track track = new Track(10,10,10);
        // update sim object

        // add sim object
        sim.addObject(track);
    }
}
