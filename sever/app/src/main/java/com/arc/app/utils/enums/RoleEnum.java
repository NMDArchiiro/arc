package com.arc.app.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ROLE_ADMIN( 1L,"ROLE_ADMIN"),
    ROLE_PROVINCE( 2L,"ROLE_PROVINCE"),
    ROLE_DISTRICT(3L, "ROLE_DISTRICT"),
    ROLE_COMMUNE( 4L,"ROLE_COMMUNE"),;
    private Long id;
    private String name;
}
