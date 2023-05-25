package com.arc.app.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseImport<T> {
    private String code;
    private List<String> cellErrorMessage;
    private Long numberRowError;
    private Long numberRowSuccess;
    private List<T> contents;
}
