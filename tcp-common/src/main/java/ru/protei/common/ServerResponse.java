package ru.protei.common;

import java.util.List;
import java.util.Objects;


/**
 * The ServerResponse is response, which contains server reply.
 * @author Dmitry Mityushin
 */

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServerResponse)) return false;
        ServerResponse<?> response = (ServerResponse<?>) o;
        return status == response.status &&
                Objects.equals(list, response.list);
    }

    @Override
    public int hashCode() {

        return Objects.hash(status, list);
    }
}
