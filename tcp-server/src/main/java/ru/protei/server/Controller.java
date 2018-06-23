package ru.protei.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.protei.common.Command;
import ru.protei.common.ServerResponse;
import ru.protei.server.model.Word;
import ru.protei.server.service.WordService;

import java.util.ArrayList;
import java.util.List;

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

        ServerResponse response;

        Word word;
        List<Word> list = new ArrayList<Word>();
        switch (command.getCode()) {
            case 1: {
                word = wordService.find(command.getWord());
                if (word != null) {
                    list.add(word);
                    response = new ServerResponse(1, Word.class.getName(), list);
                } else {
                    response = new ServerResponse(1, null, null);
                }
                break;
            }
            case 2: {
                //TODO: fix regexp
                list = wordService.findAll(command.getWord());
                response = new ServerResponse(1, Word.class.getName(), list);
                break;
            }
            case 3: {
                word = wordService.create(
                        new Word(null, command.getWord(), command.getDescription()));
                if (word == null) {
                    response = new ServerResponse(1, null, null);
                } else {
                    list.add(word);
                    response = new ServerResponse(0, Word.class.getName(), list);
                }
                break;
            }
            case 4: {
                word = wordService.update(
                        new Word(null, command.getWord(), command.getDescription()));
                if (word == null) {
                    response = new ServerResponse(1, null, null);
                } else {
                    list.add(word);
                    response = new ServerResponse(0, Word.class.getName(), list);
                }
                break;
            }
            case 5: {
                boolean flag = wordService.delete(
                        new Word(null, command.getWord(), command.getDescription()));
                if (flag) {
                    response = new ServerResponse(0, null, null);
                } else {
                    response = new ServerResponse(1, null, null);
                }
            }
            default: {
                response = new ServerResponse(1, null, null);
            }
        }

        return GSON.toJson(response);
    }
}
