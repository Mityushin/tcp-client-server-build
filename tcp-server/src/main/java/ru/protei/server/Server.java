package ru.protei.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**
 * The Server is responsible for tcp connections.
 * @author Dmitry Mityushin
 */

public class Server {
    private static final Logger log = Logger.getLogger(Server.class);

    private Controller controller;

    public Server() {
        controller = new Controller();
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        new Server().run();
    }

    public void run() {
        int serverPort = 3345;
        ServerSocket server = null;
        try {
            log.info("Trying to start server on port " + serverPort + "...");
            server = new ServerSocket(serverPort);
            log.info("Server started successfully");

        } catch (IOException e) {
            log.fatal("Failed to create socket!", e);
            System.exit(2);
        }

        while (!server.isClosed()) {
            try {
                log.info("Waiting for client...");
                Socket client = server.accept();
                log.info("Get client");

                DataInputStream in = new DataInputStream(client.getInputStream());
                DataOutputStream out = new DataOutputStream(client.getOutputStream());

                String str;

                while (!client.isClosed()) {
                    log.info("Waiting for client request...");

                    str = in.readUTF();
                    log.info("Get client request");

                    str = controller.resolveCommand(str);

                    log.info("Sending response to client...");
                    out.writeUTF(str);
                    out.flush();
                    log.info("Successfully send");
                }

                in.close();
                out.close();
            } catch (IOException e) {
                log.error("Lose client!");
            }
        }

        try {
            log.info("Trying to stop server...");
            server.close();
            log.info("Server was stop successfully");
        } catch (IOException e) {
            log.error("Failed to stop server!", e);
        }
    }
}