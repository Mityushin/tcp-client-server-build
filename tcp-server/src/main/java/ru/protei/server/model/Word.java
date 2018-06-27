package ru.protei.server.model;

/**
 * Model Word
 * @author Dmitry Mityushin
 */
public class Word {
    private Integer id;
    private String title;
    private String description;

    /**
     * Returns {@code Word} id field.
     *
     * @return {@code Word} id field
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns {@code Word}d title field.
     *
     * @return {@code Word} title field
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns {@code Word} description field.
     *
     * @return {@code Word} description field
     */
    public String getDescription() {
        return description;
    }

    public Word() {

    }

    /**
     * Set {@code Word} id field.
     *
     * @param id {@code Word} id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set {@code Word} title field.
     *
     * @param title {@code Word} title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set {@code Word} description field.
     *
     * @param description {@code Word} description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
