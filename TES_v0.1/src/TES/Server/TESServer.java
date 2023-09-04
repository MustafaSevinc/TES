package TES.Server;

import TES.Server.Network.ServerNetworkManager;

import java.io.IOException;

public class TESServer {
    InputManager inputManager;
    Simulator simulator;
    boolean isWorking;
    ServerNetworkManager networkManager;


    public TESServer(int port) {
        isWorking = true;
        inputManager = new InputManager();
        simulator = new Simulator(0, 500);
        try {
            networkManager = new ServerNetworkManager(port);
        } catch (IOException e) {
            System.out.println("SESServer::new -> TESServer.ServerNetworkManager oluşturamadık.");
            throw new RuntimeException(e);
        }
    }

    public void start(String filePath) {
        inputManager.setInputFromFile(filePath);
        this.start();
    }

    public void start() {
        while (isWorking) {
            CommandData cmd = inputManager.readNextAsCmd();
            if (cmd != null) {
                if (cmd.getCommandName().equals("exit")) {
                    isWorking = false;
                    close();
                    return;
                }
                simulator.execute(cmd);
            }
        }
    }

    public void close() {
        inputManager.close();
        simulator.close();
    }


    private static String file1 = "C:\\Users\\stj.msevinc\\Desktop\\TES\\LETSGO.txt";
    private static String file2 = "C:\\Users\\stj.msevinc\\Desktop\\TES\\LETSGO 2.txt";


    public static void main(String[] args) {
        TESServer tesServer = new TESServer(8080);
        tesServer.start(file1);
    }

}
