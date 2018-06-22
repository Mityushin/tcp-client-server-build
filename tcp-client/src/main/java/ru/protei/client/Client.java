package ru.protei.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.protei.common.Command;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.security.InvalidParameterException;

public class Client {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static void printHelp() {
        System.out.println("This is help. Enjoy.");
    }

    public static void main(String[] args) {
        try {
            new Client().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        String address = "localhost";
        int serverPort = 3345;

        InetAddress ipAddress = InetAddress.getByName(address);
        Socket server;

        System.out.println("Start client, trying to connect to server.");

        server = new Socket(ipAddress, serverPort);

        DataInputStream in = new DataInputStream(server.getInputStream());
        DataOutputStream out = new DataOutputStream(server.getOutputStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Connection established, please insert command like");
        System.out.println("<code> <word> <description>");

        String str = null;
        String[] words = null;
        Command command;

        while (true) {
            str = reader.readLine();
            words = str.split(" ", 3);

            if (words[0].equalsIgnoreCase("help")) {
                printHelp();
                continue;
            }

            if (words[0].equalsIgnoreCase("quit")) {
                break;
            }

            try {
                command = new Command(words);

                str = GSON.toJson(command);

                out.writeUTF(str);
                out.flush();

                System.out.println("Send " + str + " to server.");

                str = in.readUTF();
                System.out.println("Get '" + str + "' from server.");

            } catch (InvalidParameterException e) {
                System.out.println("Invalid code. Please insert command like");
                System.out.println("<code> <word> <description>");
                System.out.println("Use 'help' to help, 'quit' to quit");
            }
        }

        server.close();
        System.out.println("Connection close. Quit program.");
    }
}