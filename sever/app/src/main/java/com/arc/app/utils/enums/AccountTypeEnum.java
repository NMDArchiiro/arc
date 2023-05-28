package com.arc.app.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountTypeEnum {
    VAAC(1), // vaac
    AREA(2), // khu vuc
    PROVINCE(3), // tinh
    UNDER_PROVINCE(4), // truc thuoc tinh
    DISTRICT(5), // huyen
    UNDER_DISTRICT(6), // truc thuoc huyen
    COMMUNE(7); // xa
    private Integer key;
}
