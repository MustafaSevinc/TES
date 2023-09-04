package TES.Server;

import TES.Server.Datas.CommandData;
import TES.Server.Input.InputManager;
import TES.Server.Network.ServerNetworkManager;

import java.io.IOException;

public class TESServer {
    InputManager inputManager;
    Simulator simulator;
    boolean isWorking;
    ServerNetworkManager networkManager;
    Thread clientAcceptThread;


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
        startClientAcceptThread();
        while (isWorking) {
            CommandData cmd = inputManager.readNextAsCmd();
            if (cmd != null) {
                if (cmd.getCommandName().equals("exit")) {
                    isWorking = false;
                    close();
                    return;
                }
                simulator.execute(cmd);
                networkManager.sendChangesToClients(simulator.getChanges());
            }
        }
    }

    public void close() {
        clientAcceptThread.interrupt();
        inputManager.close();
        simulator.close();
    }

    private void startClientAcceptThread(){
         clientAcceptThread = new Thread(() -> {
            try {
                networkManager.acceptClients();
            } catch (IOException e) {
                System.out.println("ServerNetworkManager::startClientThread -> acceptClients() hatası");
                throw new RuntimeException(e);
            }
        });
        clientAcceptThread.start();
    }









    private static String file1 = "C:\\Users\\stj.msevinc\\Desktop\\TES\\LETSGO.txt";
    private static String file2 = "C:\\Users\\stj.msevinc\\Desktop\\TES\\LETSGO 2.txt";


    public static void main(String[] args) {
        TESServer tesServer = new TESServer(8080);
        tesServer.start(file1);
    }

}
