package com.arc.app.response;

import java.util.List;

public class ResponseList<T> {
    private Integer status;
    private String message;
    private List<T> contents;

    public ResponseList() {

    }

    public ResponseList(Integer status, String message, List<T> contents) {
        this.status = status;
        this.message = message;
        this.contents = contents;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getContents() {
        return contents;
    }

    public void setContents(List<T> contents) {
        this.contents = contents;
    }
}
