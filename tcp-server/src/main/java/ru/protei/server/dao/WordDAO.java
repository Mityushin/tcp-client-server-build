package ru.protei.server.dao;

import ru.protei.server.model.Word;

import java.util.List;

public class WordDAO {
    private static WordDAO instance = null;

    private WordDAO() {}

    public static WordDAO getInstance() {
        if (instance == null) {
            instance = new WordDAO();
        }
        return instance;
    }

    public Word find(String title) {
        return null;
    }
    public List<Word> findAll(String mask) {
        return null;
    }
    public Word save(Word w) {
        return null;
    }
    public Word modify(Word w) {
        return null;
    }
    public boolean delete(Word w) {
        return false;
    }
    public boolean exists(Integer id) {
        return false;
    }
}
