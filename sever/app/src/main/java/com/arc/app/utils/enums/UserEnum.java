package com.arc.app.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public enum UserEnum {
    ADMIN("admin", "123456",List.of(RoleEnum.ROLE_PROVINCE)),
    PROVINCE("province", "123456", List.of(RoleEnum.ROLE_PROVINCE)),
    DISTRICT("district", "123456", List.of(RoleEnum.ROLE_DISTRICT)),
    COMMUNE("commune", "123456", List.of(RoleEnum.ROLE_COMMUNE));
    private String username;
    private String password;
    private List<RoleEnum> roles;
}
