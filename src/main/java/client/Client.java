package client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client {
    private String homeDir = "./src/main/resources/client/";
    private String host = "localhost";
    private int port = 8188;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;

    public Client() {
        try{
            Socket socket = new Socket(host, port);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            Thread readThread = new Thread(() ->{
                try{
                    while (true){
                        String status = inputStream.readUTF();
                        System.out.println(status);
                    }
                } catch (Exception e){
                    System.err.println("Exception while read.");
                }
            });
            readThread.setDaemon(true);
            readThread.start();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.logic();
    }

    private void logic() {
        Scanner scanner = new Scanner(System.in);
        while (true){
            String msg = scanner.nextLine();
            String[] cmd = msg.split(" ");
            if (cmd[0].equals("#upload")) {
                sendFile(cmd[1]);
            }
        }
    }

    private void sendFile(String fileName) {
        File file = new File(homeDir + fileName);
        long length = file.length();
        try{
            outputStream.writeUTF("#upload");
            outputStream.writeUTF(fileName);
            outputStream.writeLong(length);
            Files.copy(Paths.get(homeDir + fileName), outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}