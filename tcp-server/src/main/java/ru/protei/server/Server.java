package ru.protei.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

public class Server {
    private static final Logger log = Logger.getLogger(Server.class);

    private Controller controller;

    public Server() {
        controller = Controller.getInstance();
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        try {
            new Server().run();
        } catch (IOException e) {
            log.error("Lose client", e);
        }
    }

    public void run() throws IOException{
        int serverPort = 3345;
        ServerSocket server;
        server = new ServerSocket(serverPort);
        log.info("Start server");

        Socket client = server.accept();
        log.info("Get client");

        DataInputStream in = new DataInputStream(client.getInputStream());
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        String str;

        while (!client.isClosed()) {
            str = in.readUTF();
//            log.info("Get: " + str);

            str = controller.resolveCommand(str);

            out.writeUTF(str);
            out.flush();
//            log.info("Send: " + str);
        }

        in.close();
        out.close();

        log.info("Stop server");
    }
}