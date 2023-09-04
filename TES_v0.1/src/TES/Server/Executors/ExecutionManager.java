package TES.Server.Executors;
import TES.Server.CommandData;
import java.util.HashMap;

public class ExecutionManager {
    private HashMap<String, ExecutorBase> executors = new HashMap<>();

    public void registerExecutor(String cmdName, ExecutorBase executor) {
        executors.put(cmdName, executor);
    }

    public boolean execute(CommandData cmd) {
        ExecutorBase ex = executors.get(cmd.getCommandName());
        if (ex == null)
            return false;
        return ex.execute(cmd);
    }
}
