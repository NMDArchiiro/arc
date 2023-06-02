package com.arc.app.service.base;

import com.arc.app.entity.base.Role;
import com.arc.app.request.base.RoleRequest;
import com.arc.app.response.ResponseList;
import com.arc.app.response.ResponseObject;

public interface RoleService {
    ResponseList<Role> getAllRoles();

    ResponseObject saveRole(RoleRequest request);

    Boolean duplicateRole(String roleName);
}
