package TES.SimCommands;

import java.util.HashMap;

import TES.SimObjects.*;
import TES.Simulator;

import java.util.List;

public class AddTrack extends CommandBase {
    long id;
    private double latitude;
    private double longitude;
    private double speed;
    private double altitude;
    private double course;
    private double heading;
    private boolean isMoving;

    static {
        CommandBase.commandNames.put("addTrack", (params) -> new AddTrack(params));
    }


    public AddTrack(HashMap<String, String> keyValue) {
        super("addtrack", keyValue, (params) -> new AddTrack(params));
    }


    public void execute(Simulator sim) {



        Track track = new Track(10, 10, 10);
        sim.addObject(track);

    }

    @Override
    public void setParams(HashMap<String, String> params) {

        id = Long.parseLong(params.get("id"));
        latitude = Double.parseDouble(params.get("lat"));
        longitude = Double.parseDouble(params.get("lon"));


    }
}
