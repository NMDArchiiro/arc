package com.arc.app.utils.constants;

import com.arc.app.utils.enums.RoleEnum;

public class ARCConstants {
    public static final Long TIME_OUT_TOKEN = 15L; // minutes 15p
    public static final Long TIME_OUT_REFRESH_TOKEN = 30L; // minutes 30p
    public static final String ROLE_ADMIN = RoleEnum.ROLE_ADMIN.getName();
    public static final String ROLE_PROVINCE = RoleEnum.ROLE_PROVINCE.getName();
    public static final String ROLE_DISTRICT = RoleEnum.ROLE_DISTRICT.getName();
    public static final String ROLE_COMMUNE = RoleEnum.ROLE_COMMUNE.getName();
}
