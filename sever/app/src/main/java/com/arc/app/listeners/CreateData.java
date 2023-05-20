package com.arc.app.listeners;

import com.arc.app.entity.Role;
import com.arc.app.request.RoleRequest;
import com.arc.app.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CreateData {
    @Resource
    private RoleService roleService;
    public void createRole(String roleName) {
        if(!roleService.duplicateRole(roleName)) {
            RoleRequest request = new RoleRequest();
            request.setName(roleName);
            roleService.saveRole(request);
        }
    }
}
