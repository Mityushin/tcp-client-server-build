package ru.protei.common;

import java.security.InvalidParameterException;

@Deprecated
public class Command {
    private int code;
    private String word = null;
    private String description = null;

    public Command(int code, String word, String description) {
        this.code = code;
        this.word = word;
        this.description = description;
    }

    public Command(String[] words) throws InvalidParameterException {
        if (words == null) {
            throw new NullPointerException();
        }
        try {
            this.code = Integer.parseInt(words[0]);
            this.word = words[1].toLowerCase();
            this.description = words[2];
        } catch (ArrayIndexOutOfBoundsException e) {
            //none
        } catch (NumberFormatException e) {
            throw new InvalidParameterException();
        }
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
}