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

    private WordDAO wordDAO;

    public WordService() {
        wordDAO = new WordDAO();
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
        return wordDAO.create(w);
    }

    public boolean update(Word w) {
        return wordDAO.update(w);
    }

    public boolean delete(Word w) {

        if (!wordDAO.exists(w)) {
            return false;
        }

        return wordDAO.delete(w);
    }

    public boolean exists(Word w) {
        return wordDAO.exists(w);
    }
}
