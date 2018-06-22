package ru.protei.server.model;

public class Word {
    private Integer id;
    private String title;
    private String description;

    public Word(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
