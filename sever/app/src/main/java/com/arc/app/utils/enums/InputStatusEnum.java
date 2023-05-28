package com.arc.app.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InputStatusEnum {
    LOCK(-1), // khoa
    UN_LOCK(0), // khong khoa
    COMPLETE(1), // hoan thanh nhap
    SAME_CONFIRM(2), // cung cap xac nhan
    SAME_NO_CONFIRM(3), // cung cap tu choi
    REQUEST_EDIT(4), // yeu cau chinh sua
    SEND_SUPERIORS(5); // gui len cap tren
    private Integer key;

}
