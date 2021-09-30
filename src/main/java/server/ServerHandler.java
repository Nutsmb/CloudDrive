package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerHandler implements Runnable{
    private Socket socket;
    private byte[] buffer;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ServerHandler(Socket socket){
        this.socket = socket;
        buffer = new byte[256];
    }
    @Override
    public void run() {

        try{
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            while (true){
                int readed = inputStream.read(buffer);
                System.out.println("Received: " + new String(buffer, 0, readed));
            }
        } catch (Exception e){
            System.err.println("Client connection exception");
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
