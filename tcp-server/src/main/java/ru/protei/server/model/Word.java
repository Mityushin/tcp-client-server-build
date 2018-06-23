package ru.protei.server.model;

public class Word {
    private Integer id;
    private String title;
    private String description;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Word() {

    }

    public Word(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
