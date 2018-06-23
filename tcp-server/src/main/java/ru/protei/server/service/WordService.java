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

    public Word find(String title) {
        return wordDAO.find(title);
    }
    public List<Word> findAll(String mask) {
        return wordDAO.findAll(mask);
    }
    public Word create(Word w) {
        return wordDAO.create(w);
    }
    public Word update(Word w) {
        return wordDAO.update(w);
    }
    public boolean delete(Word w) {
        return wordDAO.delete(w);
    }
    public boolean exists(Integer id) {
        return wordDAO.exists(id);
    }
}
