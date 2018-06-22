package ru.protei.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Controller controller;

    public Server() {
        controller = Controller.getInstance();
    }

    public static void main(String[] args) {
        try {
            new Server().run();
        } catch (IOException e) {
            System.out.println("Lose connection to client");
//            e.printStackTrace();
        }
    }

    public void run() throws IOException{
        int serverPort = 3345;
        ServerSocket server = null;
        server = new ServerSocket(serverPort);

        System.out.println("Start server, waiting for client.");
        Socket client = server.accept();

        DataInputStream in = new DataInputStream(client.getInputStream());
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        System.out.println("Get client.");

        String str;

        while (!client.isClosed()) {
            str = in.readUTF();

            str = controller.resolveCommand(str);

            out.writeUTF(str);
            out.flush();

            System.out.println("Send " + str + " to client.");
        }

        in.close();
        out.close();
    }
}