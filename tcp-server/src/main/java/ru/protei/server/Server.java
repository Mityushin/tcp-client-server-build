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
        new Server().run();
    }

    public void run() {
        int serverPort = 3345;
        ServerSocket server = null;
        try {
            server = new ServerSocket(serverPort);
        } catch (IOException e) {
            log.fatal("Can't create socket", e);
            System.exit(2);
        }
        log.info("Start server");

        while (true) {
            try {
                Socket client = server.accept();
                log.info("Get client");

                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                String str;

                while (!client.isClosed()) {
                    str = in.readUTF();

                    str = controller.resolveCommand(str);

                    out.writeUTF(str);
                    out.flush();
                }

                in.close();
                out.close();
            } catch (IOException e) {
                log.error("Lose client");
            }
        }

        //log.info("Stop server");
    }
}