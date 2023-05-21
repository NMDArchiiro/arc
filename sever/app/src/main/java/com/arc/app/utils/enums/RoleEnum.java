package com.arc.app.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ROLE_ADMIN( 1L,"admin"),
    ROLE_PROVINCE( 2L,"province"),
    ROLE_DISTRICT(3L, "district"),
    ROLE_COMMUNE( 4L,"commune");
    private Long id;
    private String roleName;
}
