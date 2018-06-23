package ru.protei.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.protei.common.ClientRequest;
import ru.protei.common.ServerResponse;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

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

        String str;
        String[] words;

        ClientRequest request;

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

            request = new ClientRequest();
            try {
                request.setCode(Integer.parseInt(words[0]));
                switch (request.getCode()) {
                    case 1: {
                        request.setWord(words[1]);
                        break;
                    }
                    case 2: {
                        request.setRegexp(words[1]);
                        break;
                    }
                    case 3:
                    case 4: {
                        request.setWord(words[1]);
                        request.setDescription(words[2]);
                        break;
                    }
                    case 5: {
                        request.setWord(words[1]);
                        break;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid command.\n" +
                        "Please insert command like:\n" +
                        "<code> <word> <description>");
                continue;
            } catch (NumberFormatException e) {
                System.out.println("Invalid code command.\n" +
                        "Please insert command like:\n" +
                        "<code> <word> <description>");
                continue;
            }

            str = GSON.toJson(request);

            out.writeUTF(str);
            out.flush();

            System.out.println("Send " + str + " to server.");

            str = in.readUTF();
            System.out.println("Get " + str + " from server.");

            ServerResponse response = GSON.fromJson(str, ServerResponse.class);

            if (response.getStatus() != 0) {
                System.out.println("Server returns ERROR.");
            } else {
                System.out.println("Server returns OK.");
            }
        }

        server.close();
        System.out.println("Connection close. Quit program.");
    }
}