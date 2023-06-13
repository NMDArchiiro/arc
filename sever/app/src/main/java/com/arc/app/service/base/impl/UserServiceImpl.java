package com.arc.app.service.base.impl;

import com.arc.app.entity.base.Role;
import com.arc.app.entity.base.User;
import com.arc.app.repository.base.RoleRepository;
import com.arc.app.repository.base.UserRepository;
import com.arc.app.request.base.RoleRequest;
import com.arc.app.request.base.UserRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import com.arc.app.service.base.UserSecurityService;
import com.arc.app.service.base.UserService;
import com.arc.app.utils.ConvertUtils;
import com.arc.app.utils.enums.ResponseEnum;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * author: NMDuc
 **/
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private UserSecurityService userSecurityService;

    @Resource
    private RoleRepository roleRepository;

    @Override
    public ResponseObject find(Long id) {
        try {
            User user = userRepository.findById(id).orElse(null);
            return new ResponseObject(ResponseEnum.SUCCESS.getStatus(), ResponseEnum.SUCCESS.getMessage(), new UserRequest(user));
        } catch (Exception e) {
            return new ResponseObject(ResponseEnum.ERROR.getStatus(), ResponseEnum.ERROR.getMessage(), null);
        }
    }

    @Override
    public UserRequest saveUser(UserRequest request) {
        try {
            if(ObjectUtils.isEmpty(request)) {
               return null;
            }
            User user = null;
            if(request.getId() != null) {
                user = userRepository.findById(request.getId()).orElse(null);
            }
            if(user == null) {
                if(request.getUsername() == null || request.getPassword() == null) {
                    return null;
                }
                user = new User();
                user.setUsername(request.getUsername());
            }
            user.setPassword(ConvertUtils.getHashPassword(request.getPassword()));
            user.setDisplayName(request.getDisplayName());
            user.setEmail(request.getEmail());
            user.setAccountType(request.getAccountType());
            user.setActive(request.getActive());
            user.setHasHRIReport(request.getHasHRIReport());
            user.setHasATHReport(request.getHasATHReport());
            user.setHasPOCReport(request.getHasPOCReport());
            user.setHasMDReport(request.getHasMDReport());
            user.setHasARVReport(request.getHasARVReport());
            user.setHasCIReport(request.getHasCIReport());
            user.setHasMTCReport(request.getHasMTCReport());
            user.setHasPREPReport(request.getHasPREPReport());
            user.setHasCReport(request.getHasCReport());
            user.setHasRPREPReport(request.getHasRPREPReport());
            user.setHasCIHCReport(request.getHasCIHCReport());
            user.setHasHGReport(request.getHasHGReport());
            user.setHasSPReport(request.getHasSPReport());
            user.setHasICReport(request.getHasICReport());
            user.setHasHIReport(request.getHasHIReport());
            Set<Role> listRole = new HashSet<>();
            if(!CollectionUtils.isEmpty(request.getRoles())) {
                for(RoleRequest roleRequest : request.getRoles()) {
                    Role role = roleRepository.findById(roleRequest.getId()).orElse(null);
                    if(role == null) {
                        return null;
                    }
                    listRole.add(role);
                }
            }
            if(!CollectionUtils.isEmpty(user.getRoles())) {
                user.getRoles().clear();
                user.getRoles().addAll(listRole);
            } else {
                user.setRoles(listRole);
            }
            user = userRepository.save(user);
            return new UserRequest(user);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ResponseObject changePassword(UserRequest request) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    @Override
    public ResponseObject setLocked(Long id) {
        return null;
    }

    @Override
    public Page<UserRequest> getPage(SearchRequest request) {
        return null;
    }
}
