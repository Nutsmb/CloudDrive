package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        private int port = 8188;
        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            Socket socket = serverSocket.accept();
        }
    }
}
