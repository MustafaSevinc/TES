package TES;

import TES.SimCommands.AddTrack;
import TES.SimCommands.CommandBase;

import java.util.HashMap;

public class TESServer {
    InputManager inputManager;
    Simulator simulator;
    Parser parser;
    boolean isWorking;

    public TESServer() {
        isWorking = true;
    }


    public void start() {
        this.start(null);
    }

    public void start(String filePath) {

        inputManager = new InputManager();
        simulator = new Simulator();
        parser = new Parser();

        if (filePath != null) {
            inputManager.setInputFromFile(filePath);
        }

        String inputText;
        while (isWorking) {
            inputText = inputManager.readNext();
            if (inputText.equals("exit")) {
                isWorking = false;
                close();
                return;
            }

            Object[] comAndKeyValue = parser.parse(inputText);

            String commandName = (String) comAndKeyValue[0];
            HashMap keyValue = (HashMap) comAndKeyValue[1];
            CommandBase command = inputManager.getNewCommandByName(commandName,keyValue);
            command.execute(simulator);


        }
    }

    public void close() {
        inputManager.close();
        simulator.close();
    }


    public static void main(String[] args) {
        TESServer tesServer = new TESServer();
        tesServer.start();
    }

}
