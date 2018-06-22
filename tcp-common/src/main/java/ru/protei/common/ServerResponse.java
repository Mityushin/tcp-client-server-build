package ru.protei.common;

import java.util.List;

public class ServerResponse<E> {
    private int status;
    private String className;
    private List<E> list;

    public ServerResponse(int status, String className, List<E> list) {
        this.status = status;
        this.className = className;
        this.list = list;
    }
}
