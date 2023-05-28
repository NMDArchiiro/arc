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
        createData.createRole(RoleEnum.ROLE_ADMIN.getId(), RoleEnum.ROLE_ADMIN.getName());
        createData.createRole(RoleEnum.ROLE_EDIT.getId(), RoleEnum.ROLE_EDIT.getName());
        createData.createRole(RoleEnum.ROLE_CONFIRM.getId(), RoleEnum.ROLE_CONFIRM.getName());
        createData.createRole(RoleEnum.ROLE_VIEW.getId(), RoleEnum.ROLE_VIEW.getName());
        // Create user
        createData.createUser(UserEnum.ADMIN.getUsername(), UserEnum.ADMIN.getPassword(), List.of(RoleEnum.ROLE_ADMIN));

    }
}
