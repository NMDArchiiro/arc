package com.arc.app.listeners;

import com.arc.app.utils.enums.RoleEnum;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class ApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    private CreateData createData;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        createData.createRole(RoleEnum.ROLE_ADMIN.getRoleName());
        createData.createRole(RoleEnum.ROLE_PROVINCE.getRoleName());
        createData.createRole(RoleEnum.ROLE_DISTRICT.getRoleName());
        createData.createRole(RoleEnum.ROLE_COMMUNE.getRoleName());
    }
}
