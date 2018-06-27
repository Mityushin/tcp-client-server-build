package ru.protei.client;

import org.apache.log4j.Logger;
import ru.protei.common.ClientRequest;
import ru.protei.common.ServerResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Controller {
    private static Logger log = Logger.getLogger(Controller.class);
    private static String HELP_QUERY = "help()";
    private static String QUIT_QUERY = "quit()";
    private static String HELP_BODY = "Input command like below\n" +
            "<code> <word> <description>";
    private static String BAD_COMMAND = "Invalid input. Use " + HELP_QUERY + " to help";


    private static Controller instanse;

    private Controller() {
    }

    public static Controller getInstanse() {
        if (instanse == null) {
            instanse = new Controller();
        }
        return instanse;
    }

    public ClientRequest getClientRequest() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String str;
        String[] words = null;

        ClientRequest request;

        log.info(HELP_BODY);

        while (true) {
            try {

                str = reader.readLine();
                words = str.split("\\s", 3);

                while (words[0].equalsIgnoreCase(HELP_QUERY)) {
                    log.info(HELP_BODY);

                    str = reader.readLine();
                    words = str.split("\\s", 3);
                }
            } catch (IOException e) {
                log.fatal("Can't read from input", e);
                System.exit(2);
            }

            if (words[0].equalsIgnoreCase(QUIT_QUERY)) {
                log.info("Quit program");
                System.exit(0);
            }

            request = new ClientRequest();
            try {
                request.setCode(Integer.parseInt(words[0]));
                switch (request.getCode()) {
                    case 1: {
                        request.setWord(words[1]);
                        return request;
                    }
                    case 2: {
                        request.setRegexp(words[1]);
                        return request;
                    }
                    case 3:
                    case 4: {
                        request.setWord(words[1]);
                        request.setDescription(words[2]);
                        return request;
                    }
                    case 5: {
                        request.setWord(words[1]);
                        return request;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                log.error(BAD_COMMAND);

            } catch (NumberFormatException e) {
                log.error(BAD_COMMAND);
            }
        }
    }

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
