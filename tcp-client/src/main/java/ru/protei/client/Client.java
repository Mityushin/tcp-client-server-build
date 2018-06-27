package ru.protei.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import ru.protei.common.ClientRequest;
import ru.protei.common.ServerResponse;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * The Client is responsible for create tcp connection with Server.
 * @author Dmitry Mityushin
 */

public class Client {
    private static final Logger log = Logger.getLogger(Client.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final String SERVER_IP_ADDRESS = "localhost";
    private static final int SERVER_PORT = 3345;

    private Controller controller;

    public Client() {
        this.controller = Controller.getInstanse();
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        new Client().run();
    }

    public void run() {
        try {
            log.info("Start client");

            InetAddress ipAddress = InetAddress.getByName(SERVER_IP_ADDRESS);
            Socket server = new Socket(ipAddress, SERVER_PORT);
            log.info("Connected to server");

            DataInputStream in = new DataInputStream(server.getInputStream());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());

            while (!server.isClosed()) {
                ClientRequest request = controller.getClientRequest();

                String str = GSON.toJson(request);

                out.writeUTF(str);
                out.flush();

                str = in.readUTF();

                ServerResponse response = GSON.fromJson(str, ServerResponse.class);

                controller.resolveServerResponse(response);
            }
        } catch (IOException e) {
            log.fatal("Can't connect to server", e);
            System.exit(2);
        }
    }
}