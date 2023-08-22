package TES.SimCommands;

import java.util.HashMap;

import TES.InputManager;
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


    static{
        commandNames.put("addTrack", AddTrack::new);
    }

    public AddTrack(HashMap<String, String> keyValue) {
        super("addtrack", keyValue);
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
