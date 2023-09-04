package TES.Server.Network;

import TES.Server.Datas.CommandData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerNetworkManager {
    private ServerSocket serverSocket;
    private ArrayList<ClientHandler> clients;
    private boolean isOnline;

    public ServerNetworkManager(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.clients = new ArrayList<>();
        isOnline = true;
    }

    public void acceptClients() throws IOException {
        while(isOnline){
            Socket clientSocket = serverSocket.accept();
            System.out.println("TESServer.ServerNetworkManager::acceptClients -> TES.Client bağlandı");
            ClientHandler clientHandler = new ClientHandler(clientSocket);
            clients.add(clientHandler);
            new Thread(clientHandler).start();
        }
    }



    public void sendChangesToClients(ArrayList<CommandData> changes){
        for(ClientHandler client: clients){
            client.sendChanges(changes);
        }
    }



}
