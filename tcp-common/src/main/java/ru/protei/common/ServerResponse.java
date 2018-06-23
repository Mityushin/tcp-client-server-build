package ru.protei.common;

import java.util.List;

public class ServerResponse<E> {
    private int status;
    private List<E> list;

    public ServerResponse() {
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
