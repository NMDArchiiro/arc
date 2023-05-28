package com.arc.app.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ROLE_ADMIN( 1L,"ROLE_ADMIN"),
    ROLE_EDIT( 2L,"ROLE_EDIT"),
    ROLE_CONFIRM( 3L,"ROLE_CONFIRM"),
    ROLE_VIEW(4L, "ROLE_VIEW");
    private Long id;
    private String name;
}
