package com.arc.app.service.impl;

import com.arc.app.entity.Role;
import com.arc.app.entity.User;
import com.arc.app.repository.RoleRepository;
import com.arc.app.repository.UserRepository;
import com.arc.app.request.RoleRequest;
import com.arc.app.request.UserRequest;
import com.arc.app.response.UserResponse;
import com.arc.app.service.UserSecurityService;
import com.arc.app.utils.ConvertUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
public class UserSecurityServiceImpl implements UserSecurityService, UserDetailsService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRequest userRequest = userRepository.findByUsername(username);
        if(ObjectUtils.isEmpty(userRequest)) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(!CollectionUtils.isEmpty(userRequest.getRoles())) {
            userRequest.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new org.springframework.security.core.userdetails.User(userRequest.getUsername(), userRequest.getPassword(), authorities);
        }
        return null;
    }
    @Override
    public UserRequest saveUser(UserRequest request) {
        if(request != null) {
            User user = null;
            if(request.getId() != null) {
                user = userRepository.findById(request.getId()).orElse(null);
            }
            if(user == null) {
                user = new User();
            }
            user.setUsername(request.getUsername());
            user.setPassword(ConvertUtils.getHashPassword(request.getPassword()));
            Set<Role> listRole = new HashSet<>();
            for(RoleRequest roleRequest : request.getRoles()) {
                Role role = roleRepository.findById(roleRequest.getId()).orElse(null);
                if(role == null) {
                    role = new Role();
                }
                role.setName(roleRequest.getName());
                listRole.add(role);
            }
            if(!CollectionUtils.isEmpty(user.getRoles())) {
                user.getRoles().clear();
                user.getRoles().addAll(listRole);
            } else {
                user.setRoles(listRole);
            }
            user = userRepository.save(user);
            return new UserRequest(user);
        }
        return null;
    }

    @Override
    public Boolean duplicateUser(String username) {
        try {
            Long countDuplicate = userRepository.countDuplicate(username);
            return countDuplicate > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserResponse getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserResponse(user);
    }

    @Override
    public boolean isAdmin() {
        return false;
    }

    @Override
    public boolean hasHRIReport() {
        return false;
    }

    @Override
    public boolean hasATHReport() {
        return false;
    }

    @Override
    public boolean hasMDReport() {
        return false;
    }

    @Override
    public boolean hasARVReport() {
        return false;
    }

    @Override
    public boolean hasCIReport() {
        return false;
    }

    @Override
    public boolean hasMTCReport() {
        return false;
    }

    @Override
    public boolean hasCReport() {
        return false;
    }

    @Override
    public boolean hasPREPReport() {
        return false;
    }

    @Override
    public boolean hasPOCReport() {
        return false;
    }

    @Override
    public boolean hasCIHCReport() {
        return false;
    }

    @Override
    public boolean hasRPREPReport() {
        return false;
    }

    @Override
    public boolean hasHGReport() {
        return false;
    }

    @Override
    public boolean hasSPReport() {
        return false;
    }

    @Override
    public boolean hasICReport() {
        return false;
    }

    @Override
    public boolean hasHIReport() {
        return false;
    }

    @Override
    public boolean isRoleView() {
        return false;
    }

    @Override
    public boolean isRoleEdit() {
        return false;
    }

    @Override
    public boolean isRoleConfirm() {
        return false;
    }

    @Override
    public Integer getAccountType() {
        return null;
    }
}
