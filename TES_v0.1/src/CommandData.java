import java.util.Map;

public class CommandData {
    private String commandName;
    private Map<String,String> args;

    public CommandData(String commandName, Map<String, String> args) {
        this.commandName = commandName;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }
    public Map<String, String> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return "CommandData{" +
                "commandName='" + commandName + '\'' +
                ", args=" + args +
                '}';
    }
}
