package ru.protei.common;

import java.util.List;

public class ServerResponse<E> {
    private int status;
    @Deprecated
    private String className;
    private List<E> list;

    public ServerResponse() {
    }

    @Deprecated
    public ServerResponse(int status, String className, List<E> list) {
        this.status = status;
        this.className = className;
        this.list = list;
    }

    @Deprecated
    public ServerResponse(int status, List<E> list) {
        this.status = status;
        this.list = list;
    }

    public int getStatus() {
        return status;
    }

    public List<E> getList() {
        return list;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setList(List<E> list) {
        this.list = list;
    }
}
