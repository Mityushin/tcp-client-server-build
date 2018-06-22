package ru.protei.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.protei.common.Command;
import ru.protei.common.ServerResponse;
import ru.protei.server.service.WordService;

public class Controller {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Controller instance = null;
    private WordService wordService;

    private Controller() {
        wordService = WordService.getInstance();
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

        ServerResponse responce = wordService.resolveCommand(command);

        return GSON.toJson(responce);
    }
}
