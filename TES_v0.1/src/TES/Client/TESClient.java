package TES.Client;

import java.io.IOException;

public class TESClient {
    ClientNetworkManager networkManager;

    public TESClient(String serverAddress, int port){
        networkManager = new ClientNetworkManager(serverAddress, port);
    }

    private void start() {
        try {
            networkManager.connectToServer();
            while (true)
                networkManager.receiveChanges();
        } catch (IOException e) {
            System.out.println("TESClient::start -> Connection to Server Failed");
            throw new RuntimeException(e);
        }

    }




    public static void main(String[] args) {
        TESClient tesClient = new TESClient("localhost",8080);
        tesClient.start();
    }

}
