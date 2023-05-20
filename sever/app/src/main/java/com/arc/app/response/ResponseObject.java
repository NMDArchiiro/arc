package com.arc.app.response;

import java.io.Serializable;

public class ResponseObject implements Serializable {
    private Integer status;
    private String message;
    private Object body;

    public ResponseObject() {

    }

    public ResponseObject(Integer status, String message, Object body) {
        this.status = status;
        this.message = message;
        this.body = body;
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

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
