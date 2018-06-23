package ru.protei.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.protei.common.ClientRequest;
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

        ClientRequest request = GSON.fromJson(str, ClientRequest.class);
        ServerResponse<Word> response = new ServerResponse<Word>();
        response.setStatus(1);

        Word word;
        List<Word> list = new ArrayList<Word>();
        int status = 1;
        switch (request.getCode()) {
            case 1: {
                word = wordService.find(request.getWord());
                if (word != null) {
                    list.add(word);
                }
                response.setStatus(0);
                break;
            }
            case 2: {
                //TODO: fix regexp
                list = wordService.findAll(request.getWord());
                response.setStatus(0);
                break;
            }
            case 3: {
                word = wordService.create(
                        new Word(null, request.getWord(), request.getDescription()));
                if (word != null) {
                    list.add(word);
                    response.setStatus(0);
                }
                break;
            }
            case 4: {
                word = wordService.update(
                        new Word(null, request.getWord(), request.getDescription()));
                if (word != null) {
                    list.add(word);
                    response.setStatus(0);
                }
                break;
            }
            case 5: {
                boolean flag = wordService.delete(
                        new Word(null, request.getWord(), request.getDescription()));
                if (flag) {
                    response.setStatus(0);
                }
                break;
            }
        }
        response.setList(list);

        return GSON.toJson(response);
    }
}
