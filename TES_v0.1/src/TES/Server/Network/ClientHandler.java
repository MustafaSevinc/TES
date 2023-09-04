package TES.Server.Network;

import TES.Server.CommandData;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private OutputStream outputStream;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.outputStream = clientSocket.getOutputStream();
    }

    @Override
    public void run() {
        // TES.Client ile iletişim kurma kodu burada yer alır
        // Örneğin, gelen verileri okuma ve işleme
    }





    public void sendChanges(ArrayList<CommandData> changes) {
        try {
            for (CommandData change : changes) {
                outputStream.write((change + "\n").getBytes());
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            outputStream.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
