package TES.Server.Executors;

import TES.Server.Datas.CommandData;
import TES.Server.Simulator;

public class WaitCmdExecutor extends ExecutorBase {

    private Simulator sim;

    public WaitCmdExecutor(Simulator sim) {
        this.sim = sim;
    }

    @Override
    public boolean execute(CommandData cmd) {
        System.out.printf("TESServer.WaitCmdExecutor::execute - %s%n",cmd.toString());

        String strSec = cmd.getArgs().get("sec");

        if(strSec == null){
            System.out.printf("give id with value: %s sec 5",cmd.getCommandName());
            return false;
        }

        int sec = Integer.parseInt(strSec);

        try {
            Thread.sleep(sec* 1000L);
        } catch (InterruptedException e) {
            System.out.println("aa olmadııı");
        }


        return true;
    }
}
