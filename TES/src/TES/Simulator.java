package TES;

import TES.SimCommands.CommandBase;
import TES.SimObjects.SimObjectBase;

import java.util.ArrayList;
import java.util.List;

public class Simulator {

    private List<SimObjectBase> simObjects;
    public Simulator(){
        simObjects = new ArrayList<>();
    }

    public void commandReceived(CommandBase command){
        command.execute(this);
    }


    public void close(){
        simObjects = null;
    }


    public void addObject(SimObjectBase simObject){
        simObjects.add(simObject);
    }


}
