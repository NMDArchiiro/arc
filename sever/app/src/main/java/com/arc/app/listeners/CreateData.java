package com.arc.app.listeners;

import com.arc.app.request.base.RoleRequest;
import com.arc.app.request.base.UserSecurity;
import com.arc.app.service.base.RoleService;
import com.arc.app.service.base.UserSecurityService;
import com.arc.app.utils.enums.RoleEnum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateData {
    @Resource
    private RoleService roleService;

    @Resource
    private UserSecurityService userSecurityService;

    public void createRole(Long id, String roleName) {
        if(!roleService.duplicateRole(roleName)) {
            RoleRequest request = new RoleRequest();
            request.setId(id);
            request.setName(roleName);
            roleService.saveRole(request);
        }
    }

    public void createUser(String userName, String password, List<RoleEnum> roles) {
        if(!userSecurityService.duplicateUser(userName)) {
            UserSecurity request = new UserSecurity();
            request.setUsername(userName);
            request.setPassword(password);
            List<RoleRequest> roleRequests = new ArrayList<>();
            for(RoleEnum role : roles) {
                RoleRequest roleRequest = new RoleRequest();
                roleRequest.setId(role.getId());
                roleRequest.setName(role.name());
                roleRequests.add(roleRequest);
            }
            request.setRoles(roleRequests);
            userSecurityService.saveUser(request);
        }
    }
}
