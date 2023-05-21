package com.arc.app.service.impl;

import com.arc.app.entity.Role;
import com.arc.app.repository.RoleRepository;
import com.arc.app.request.RoleRequest;
import com.arc.app.response.ResponseList;
import com.arc.app.response.ResponseObject;
import com.arc.app.service.RoleService;
import com.arc.app.utils.enums.ResponseEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleRepository roleRepository;

    @Override
    public ResponseList<Role> getAllRoles() {
        try {
            List<Role> roles = roleRepository.findList();
            return new ResponseList<>(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), roles);
        } catch (Exception e) {
            return new ResponseList<>(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public ResponseObject saveRole(RoleRequest request) {
        try {
            Role role = null;
            if(request.getId() != null) {
                role = roleRepository.findById(request.getId()).orElse(null);
            }
            if(ObjectUtils.isEmpty(role)) {
                role = new Role();
            }
            role.setName(request.getName());
            role = roleRepository.save(role);
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), role);
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public Boolean duplicateRole(String roleName) {
        try {
            Long countDuplicate = roleRepository.countDuplicate(roleName);
            return countDuplicate > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
