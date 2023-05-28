package com.arc.app.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public enum UserEnum {
    ADMIN("admin", "123456",List.of(RoleEnum.ROLE_ADMIN));
    private String username;
    private String password;
    private List<RoleEnum> roles;
}
