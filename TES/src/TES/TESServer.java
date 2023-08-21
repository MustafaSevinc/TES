package TES;

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
            HashMap keyValue = parser.parse(inputText);
            CommandBase command = CommandBase.
            command.setParams(keyValue);
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
