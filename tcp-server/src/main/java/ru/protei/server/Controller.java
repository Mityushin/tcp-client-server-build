package ru.protei.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.protei.common.ClientRequest;
import ru.protei.common.ServerResponse;
import ru.protei.server.model.Word;
import ru.protei.server.service.WordService;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Controller instance;
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

        Word word = new Word();
        List<Word> list = new ArrayList<Word>();

        switch (request.getCode()) {
            case 1: {
                word.setTitle(request.getWord());

                word = wordService.find(word);
                if (word != null) {
                    list.add(word);
                    response.setStatus(0);
                }
                break;
            }
            case 2: {
                list = wordService.findAll(request.getRegexp());
                response.setStatus(0);
                break;
            }
            case 3: {
                word.setTitle(request.getWord());
                word.setDescription(request.getDescription());

                if (wordService.create(word)) {
                    response.setStatus(0);
                }
                break;
            }
            case 4: {
                word.setTitle(request.getWord());
                word.setDescription(request.getDescription());

                if (wordService.update(word)) {
                    response.setStatus(0);
                }
                break;
            }
            case 5: {
                word.setTitle(request.getWord());

                if (wordService.delete(word)) {
                    response.setStatus(0);
                }
                break;
            }
            default: {
                response.setStatus(2);
            }
        }
        response.setList(list);

        return GSON.toJson(response);
    }
}
