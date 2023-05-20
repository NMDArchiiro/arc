package com.arc.app.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ROLE_ADMIN( "admin"),
    ROLE_PROVINCE( "province"),
    ROLE_DISTRICT( "district"),
    ROLE_COMMUNE( "commune");
    private String roleName;
}
