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

        log.info("Trying to resolve command...");
        switch (request.getCode()) {
            case 1: {
                log.info("Get Find command");

                word.setTitle(request.getWord());

                log.info("Trying to find word...");
                word = wordService.find(word);
                if (word != null) {
                    log.info("Word find completed successfully");
                    list.add(word);
                } else {
                    log.info("Failed to find word");
                }
                response.setStatus(0);
                break;
            }
            case 2: {
                log.info("Get FindByRegExp command");

                log.info("Trying to find by regexp words...");
                list = wordService.findAll(request.getRegexp());
                if (!list.isEmpty()) {
                    log.info("Words find by regexp completed successfully");
                } else {
                    log.info("Nothing found");
                }
                response.setStatus(0);
                break;
            }
            case 3: {
                log.info("Get Insert command");

                word.setTitle(request.getWord());
                word.setDescription(request.getDescription());

                log.info("Trying to create word...");
                if (wordService.create(word)) {
                    log.info("Word successfully created");
                    response.setStatus(0);
                } else {
                    log.info("Word not created");
                    response.setStatus(1);
                }
                break;
            }
            case 4: {
                log.info("Get Update command");

                word.setTitle(request.getWord());
                word.setDescription(request.getDescription());

                log.info("Trying to update word...");
                if (wordService.update(word)) {
                    log.info("Word successfully updated");
                    response.setStatus(0);
                } else {
                    log.info("Word not updated");
                    response.setStatus(1);
                }
                break;
            }
            case 5: {
                log.info("Get Delete command");

                word.setTitle(request.getWord());

                log.info("Trying to delete word...");
                if (wordService.delete(word)) {
                    log.info("Word successfully deleted");
                    response.setStatus(0);
                } else {
                    log.info("Word not deleted");
                    response.setStatus(1);
                }
                break;
            }
            default: {
                response.setStatus(2);
                log.error("Get invalid command " + request.getCode());
            }
        }
        response.setList(list);

        return GSON.toJson(response);
    }
}
