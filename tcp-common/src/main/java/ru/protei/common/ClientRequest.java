package ru.protei.common;


/**
 * The ClientRequest is request, which contains command with the necessary fields.
 * @author Dmitry Mityushin
 */

public class ClientRequest {
    private int code;
    private String word;
    private String description;
    private String regexp;

    public void setCode(int code) {
        this.code = code;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRegexp(String regexp) {
        this.regexp = regexp;
    }

    public ClientRequest() {

    }

    public int getCode() {
        return code;
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }

    public String getRegexp() {
        return regexp;
    }
}
