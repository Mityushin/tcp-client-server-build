package ru.protei.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.protei.common.Command;

public class Controller {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Controller instance = null;

    private Controller() {
    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public String resolveCommand(String str) {
        System.out.println("Get\n" + str);

        Command command = GSON.fromJson(str, Command.class);

        return "OK";
    }
}
