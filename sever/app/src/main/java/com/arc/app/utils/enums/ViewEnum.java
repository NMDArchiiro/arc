package com.arc.app.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ViewEnum {
    QUARTER_COMMUNE(1), // quy xa
    QUARTER_DISTRICT(2), // quy huyen
    UNDER_QUARTER_DISTRICT(3), // quy dvtt huyen
    QUARTER_PROVINCE(4), // quy tinh
    UNDER_QUARTER_PROVINCE(5), // quy dvtt tinh
    YEAR_DISTRICT(6), // nam huyen
    UNDER_YEAR_DISTRICT(7), // nam dvtt huyen
    YEAR_PROVINCE(8), // nam tinh
    UNDER_YEAR_PROVINCE(9); // nam dvtt tinh
    private Integer key;

}
