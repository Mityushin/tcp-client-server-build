package ru.protei.server.service;

import org.apache.log4j.Logger;
import ru.protei.server.dao.WordDAO;
import ru.protei.server.model.Word;

import java.util.List;

/**
 * Service for the model {@code Word}.
 * @author Dmitry Mityushin
 */

public class WordService {
    private static final Logger log = Logger.getLogger(WordService.class);

    private static WordService instance;
    private WordDAO wordDAO;

    private WordService() {
        wordDAO = WordDAO.getInstance();
    }

    /**
     * Returns {@code WordService} instance.
     *
     * @return {@code WordService} instance
     */
    public static WordService getInstance() {

        if (instance == null) {
            instance = new WordService();
        }

        return instance;
    }

    /**
     * Find {@code Word} by {@code Word} title.
     *
     * @param w with set title
     * @return the {@code Word} of the element to be found
     * @return null if there isn't {@code Word} with specified title
     */
    public Word find(Word w) {
        return wordDAO.find(w);
    }

    public List<Word> findAll(String mask) {
        StringBuilder builder = new StringBuilder();
        for (char ch : mask.toCharArray()) {
            switch (ch) {
                case '*': {
                    builder.append('%');
                    break;
                }
                case '?': {
                    builder.append('_');
                    break;
                }
                default: {
                    builder.append(ch);
                }
            }
        }
        return wordDAO.findAll(builder.toString());
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
