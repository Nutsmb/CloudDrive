package server;

import java.io.*;
import java.net.Socket;

public class ServerHandler implements Runnable{
    private Socket socket;
    private byte[] buffer;
    private int BUFFER_SIZE = 1024;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    String directory = "./src/main/resources/server/";

    public ServerHandler(Socket socket){
        this.socket = socket;
        buffer = new byte[BUFFER_SIZE];
    }
    @Override
    public void run() {
        try{
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            while (true){
                String command = inputStream.readUTF();
                if(command.equals("#upload")){
                    String fileName = inputStream.readUTF();
                    long size = inputStream.readLong();
                    try(FileOutputStream fos = new FileOutputStream(directory + fileName)){
                        for (int i = 0; i < (size + (BUFFER_SIZE - 1)) / BUFFER_SIZE; i++) {
                            int read = inputStream.read(buffer);
                            fos.write(buffer,0, read);
                        }
                    }
                    outputStream.writeUTF("File uploaded successfully!");
                }
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