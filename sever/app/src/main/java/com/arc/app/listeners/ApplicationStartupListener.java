package com.arc.app.listeners;

import com.arc.app.utils.enums.RoleEnum;
import com.arc.app.utils.enums.UserEnum;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private CreateData createData;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Create role
        createData.createRole(RoleEnum.ROLE_ADMIN.getRoleName());
        createData.createRole(RoleEnum.ROLE_PROVINCE.getRoleName());
        createData.createRole(RoleEnum.ROLE_DISTRICT.getRoleName());
        createData.createRole(RoleEnum.ROLE_COMMUNE.getRoleName());
        // Create user
        createData.createUser(UserEnum.ADMIN.getUsername(), UserEnum.ADMIN.getPassword(), List.of(RoleEnum.ROLE_ADMIN));
        createData.createUser(UserEnum.PROVINCE.getUsername(), UserEnum.PROVINCE.getPassword(), List.of(RoleEnum.ROLE_PROVINCE));
        createData.createUser(UserEnum.DISTRICT.getUsername(), UserEnum.DISTRICT.getPassword(), List.of(RoleEnum.ROLE_DISTRICT));
        createData.createUser(UserEnum.COMMUNE.getUsername(), UserEnum.COMMUNE.getPassword(), List.of(RoleEnum.ROLE_COMMUNE));

    }
}
