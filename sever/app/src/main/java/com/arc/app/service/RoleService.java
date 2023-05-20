package com.arc.app.service;

import com.arc.app.entity.Role;
import com.arc.app.request.RoleRequest;
import com.arc.app.response.ResponseList;
import com.arc.app.response.ResponseObject;

public interface RoleService {
    ResponseList<Role> getAllRoles();

    ResponseObject saveRole(RoleRequest request);

    Boolean duplicateRole(String roleName);
}
