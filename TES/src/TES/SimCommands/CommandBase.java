package TES.SimCommands;
import TES.Simulator;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class CommandBase {

    private String commandName;

    public static Map<String, Function<HashMap<String, String>, CommandBase>> commandNames = new HashMap<>();


    public CommandBase(String commandName, HashMap<String, String> keyValue) {
        this.commandName = commandName;
        setParams(keyValue);
    }

    public abstract void execute(Simulator sim);
    public abstract void setParams(HashMap<String,String> params);


    public String getCommandName() {
        return commandName;
    }



    // parameters
    // allow set (from parser)
    // allow get (from executor/ simulator)


}
