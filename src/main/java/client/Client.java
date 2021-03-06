package client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    private String host = "localhost";
    private int port = 8188;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Client() {
        try{
            Socket socket = new Socket(host, port);
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.logic();
    }

    private void logic() {
        try{
            Scanner scanner = new Scanner(System.in);
            while (true){
                String msg = scanner.nextLine();
                outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
