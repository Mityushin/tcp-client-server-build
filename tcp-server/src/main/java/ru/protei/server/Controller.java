package ru.protei.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import ru.protei.common.ClientRequest;
import ru.protei.common.ServerResponse;
import ru.protei.server.model.Word;
import ru.protei.server.service.WordService;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller is responsible for parsing the commands from tcp connection.
 * @author Dmitry Mityushin
 */

public class Controller {
    private static final Logger log = Logger.getLogger(Controller.class);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private WordService wordService;

    public Controller() {
        wordService = new WordService();
    }

    public String resolveCommand(String str) {
        ClientRequest request = GSON.fromJson(str, ClientRequest.class);
        ServerResponse<Word> response = new ServerResponse<Word>();
        response.setStatus(1);

        Word word = new Word();
        List<Word> list = new ArrayList<Word>();

        switch (request.getCode()) {
            case 1: {
                log.info("Resolve Find command");

                word.setTitle(request.getWord());

                word = wordService.find(word);
                if (word != null) {
                    list.add(word);
                    response.setStatus(0);
                }
                break;
            }
            case 2: {
                log.info("Resolve FindByRegExp command");

                list = wordService.findAll(request.getRegexp());
                response.setStatus(0);
                break;
            }
            case 3: {
                log.info("Resolve Insert command");

                word.setTitle(request.getWord());
                word.setDescription(request.getDescription());

                if (wordService.create(word)) {
                    response.setStatus(0);
                }
                break;
            }
            case 4: {
                log.info("Resolve Update command");

                word.setTitle(request.getWord());
                word.setDescription(request.getDescription());

                if (wordService.update(word)) {
                    response.setStatus(0);
                }
                break;
            }
            case 5: {
                log.info("Resolve Delete command");

                word.setTitle(request.getWord());

                if (wordService.delete(word)) {
                    response.setStatus(0);
                }
                break;
            }
            default: {
                response.setStatus(2);
                log.error("Resolve invalid command " + request.getCode());
            }
        }
        response.setList(list);

        return GSON.toJson(response);
    }
}
