package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private BufferedReader reader;
    private Socket sock;
    private PrintWriter client;
    private final ChatServer chatServer;

    public ClientHandler(Socket clientSocket, PrintWriter user, final ChatServer outer) {
        this.chatServer = outer;
        client = user;
        try {
            sock = clientSocket;
            InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(isReader);
        } catch (Exception ex) {
            chatServer.setServerTextArea("Unexpected error... \n");
        }
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = reader.readLine()) != null) {
                chatServer.setServerTextArea("Received: " + message + "\n");
                chatServer.tellEveryone(message);
            }
        } catch (Exception ex) {
            chatServer.setServerTextArea("Lost a connection. \n");
            ex.printStackTrace();
        }
    }
    
}
