package ru.protei.server.service;

import org.apache.log4j.Logger;
import ru.protei.server.dao.WordDAO;
import ru.protei.server.model.Word;

import java.util.List;

public class WordService {
    private static final Logger log = Logger.getLogger(WordService.class);

    private static WordService instance;
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

    public Word find(Word w) {
        return wordDAO.find(w);
    }

    public List<Word> findAll(String mask) {
        return wordDAO.findAll(mask);
    }

    public boolean create(Word w) {

        boolean result = wordDAO.create(w);

        if (result) {
            log.info("Insert WORD value");
        } else {
            log.info("Can't insert WORD value");
        }

        return result;
    }

    public boolean update(Word w) {

        boolean result = wordDAO.update(w);

        if (result) {
            log.info("Update WORD value");
        } else {
            log.info("Can't update WORD value");
        }

        return result;
    }

    public boolean delete(Word w) {

        if (!wordDAO.exists(w)) {
            log.info("Can't delete WORD value. It doesn't exist");
            return false;
        }

        boolean result = wordDAO.delete(w);

        if (result) {
            log.info("Delete WORD value");
        } else {
            log.info("Can't delete WORD value");
        }

        return result;
    }

    public boolean exists(Word w) {
        return wordDAO.exists(w);
    }
}
