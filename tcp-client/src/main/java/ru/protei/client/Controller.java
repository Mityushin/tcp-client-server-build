package ru.protei.client;

import org.apache.log4j.Logger;
import ru.protei.common.ClientRequest;
import ru.protei.common.ServerResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The Controller is responsible for parsing the commands from {@code System.in}.
 * @author Dmitry Mityushin
 */

public class Controller {
    private static Logger log = Logger.getLogger(Controller.class);
    private static String HELP_QUERY = "help()";
    private static String QUIT_QUERY = "quit()";
    private static String HELP_BODY = "Input command number\n" +
            "1 - find word by title\n" +
            "2 - find word by regexp\n" +
            "3 - create word\n" +
            "4 - change word\n" +
            "5 - remove word\n" +
            "quit() - quit from program\n" +
            "help() - print this message\n";
    private static String BAD_COMMAND = "Invalid input. Use " + HELP_QUERY + " to get help";

    public Controller() {
    }

    /**
     * Returns valid request with required parameters.
     *
     * @return valid request with required parameters
     */
    public ClientRequest getClientRequest() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            log.info(HELP_BODY);

            ClientRequest request = new ClientRequest();

            try {
                String str = reader.readLine();

                while (str.equalsIgnoreCase(HELP_QUERY)) {
                    log.info(HELP_BODY);
                    str = reader.readLine();
                }

                if (str.equalsIgnoreCase(QUIT_QUERY)) {
                    log.info("Waiting for quit program...");
                    request.setCode(9);
                    return request;
                }

                request.setCode(Integer.parseInt(str));

                String[] words;
                switch (request.getCode()) {
                    case 1: {
                        log.info("Get Find command. Please input <word_title>");
                        request.setWord(reader.readLine());
                        break;
                    }
                    case 2: {
                        log.info("Get Find by regexp command. Please input <regexp>");
                        request.setRegexp(reader.readLine());
                        break;
                    }
                    case 3: {
                        log.info("Get Create word command. Please input <word_title> <word_description>");
                        words = reader.readLine().split("\\s", 2);

                        request.setWord(words[0]);
                        request.setDescription(words[1]);

                        break;
                    }
                    case 4: {
                        log.info("Get Change word command. Please input <word_title> <new_word_description>");
                        words = reader.readLine().split("\\s", 2);

                        request.setWord(words[0]);
                        request.setDescription(words[1]);

                        break;
                    }
                    case 5: {
                        log.info("Get Remove word command. Please input <word_title>");
                        request.setWord(reader.readLine());
                        break;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                log.error(BAD_COMMAND);
                continue;
            } catch (IOException e) {
                log.fatal("Failed to read from input", e);
                request.setCode(-1);
            }

            return request;
        }
    }

    /**
     * Displays server response.
     *
     * @param response with got data
     */
    public void resolveServerResponse(ServerResponse response) {
        switch (response.getStatus()) {
            case 0: {
                log.info("Server return OK");
                break;
            }
            case 1: {
                log.error("Server return Error (can't make command)");
                break;
            }
            case 2: {
                log.error("Server return Error (invalid request)");
                break;
            }
            default: {
                log.error("Get invalid status code from server");
            }
        }
        if (!response.getList().isEmpty()) {
            log.info(response.getList().toString());
        }
    }
}
