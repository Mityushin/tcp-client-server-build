package ru.protei.server.service;

import ru.protei.common.Command;
import ru.protei.common.ServerResponse;
import ru.protei.server.dao.WordDAO;
import ru.protei.server.model.Word;

import java.util.ArrayList;
import java.util.List;

public class WordService {
    private static WordService instance = null;
    private WordDAO wordDAO;

    private WordService() {
        wordDAO = WordDAO.getInstance();
    }

    public static WordService getInstance() {
        if (instance == null) {
            instance = new WordService();
        }
        return instance;
    }

    public ServerResponse resolveCommand(Command command) {
        Word word;
        List<Word> list = new ArrayList<Word>();
        switch (command.getCode()) {
            case 1: {
                word = wordDAO.find(command.getWord());
                if (word != null) {
                    list.add(word);
                } else {
                    return new ServerResponse(1, null, null);
                }
                break;
            }
            case 2: {
                //TODO: fix regexp
                list = wordDAO.findAll(command.getWord());
                break;
            }
            case 3: {
                word = wordDAO.save(
                        new Word(null, command.getWord(), command.getDescription()));
                if (word == null) {
                    return new ServerResponse(1, null, null);
                }
                break;
            }
            case 4: {
                word = wordDAO.modify(
                        new Word(null, command.getWord(), command.getDescription()));
                if (word == null) {
                    return new ServerResponse(1, null, null);
                }
                break;
            }
            case 5: {
                boolean flag = wordDAO.delete(
                        new Word(null, command.getWord(), command.getDescription()));
                if (flag) {
                    return new ServerResponse(0, null, null);
                } else {
                    return new ServerResponse(1, null, null);
                }
            }
        }

        return new ServerResponse(0, Word.class.getName(), list);
    }
}
