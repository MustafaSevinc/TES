package TES.Client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

class ClientNetworkManager {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ClientNetworkManager(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connectToServer() throws IOException {
        socket = new Socket(serverAddress, serverPort);
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
    }
    public void receiveChanges(ArrayList<String/*TESServer.CommandData olcak Aslında*/> changes) {
    }
}
