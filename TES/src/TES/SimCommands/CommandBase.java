package TES.SimCommands;

import TES.Simulator;

import java.util.HashMap;
import java.util.List;

public abstract class CommandBase {

    private String commandName;
    private HashMap keyValues;
    private List<Object> params;

    public CommandBase(String commandName, HashMap keyValues){
        this(commandName);
        this.keyValues = keyValues;
    }

    public CommandBase(String commandName){
        this.commandName = commandName;
    }

    public CommandBase(String commandName, List<Object> params){
        this(commandName);
        this.params = params;
    }






    public abstract void execute(Simulator sim);


    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public HashMap getKeyValues() {
        return keyValues;
    }

    public void setKeyValues(HashMap keyValues) {
        this.keyValues = keyValues;
    }
}
