package ru.protei.common;

public class ClientRequest {
    private int code;
    private String word;
    private String description;
    private String regexp;

    public ClientRequest(int code, String word, String description, String regexp) {
        this.code = code;
        this.word = word;
        this.description = description;
        this.regexp = regexp;
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
