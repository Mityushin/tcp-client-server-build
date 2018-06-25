package ru.protei.server.service;

import ru.protei.server.dao.WordDAO;
import ru.protei.server.model.Word;

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

    public Word find(Word w) {
        return wordDAO.find(w);
    }
    public List<Word> findAll(String mask) {
        return wordDAO.findAll(mask);
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
        wordDAO.delete(w);
        return true;
    }
    public boolean exists(Word w) {
        return wordDAO.exists(w);
    }
}
