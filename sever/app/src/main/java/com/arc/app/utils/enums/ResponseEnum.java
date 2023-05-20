package com.arc.app.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {
    SUCCESS(200, "success"),
    BAD_REQUEST(400, "bad request"),
    ERROR(500, "error");
    private Integer status;
    private String message;
}
