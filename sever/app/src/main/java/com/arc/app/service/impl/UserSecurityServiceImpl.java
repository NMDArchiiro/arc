package com.arc.app.service.impl;

import com.arc.app.entity.Role;
import com.arc.app.entity.User;
import com.arc.app.repository.RoleRepository;
import com.arc.app.repository.UserRepository;
import com.arc.app.request.RoleRequest;
import com.arc.app.request.UserRequest;
import com.arc.app.request.UserSecurity;
import com.arc.app.service.UserSecurityService;
import com.arc.app.utils.ConvertUtils;
import com.arc.app.utils.enums.RoleEnum;
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
        UserSecurity userSecurity = userRepository.findByUsername(username);
        if(ObjectUtils.isEmpty(userSecurity)) {
            throw new UsernameNotFoundException("User not found");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if(!CollectionUtils.isEmpty(userSecurity.getRoles())) {
            userSecurity.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
            return new org.springframework.security.core.userdetails.User(userSecurity.getUsername(), userSecurity.getPassword(), authorities);
        }
        return null;
    }
    @Override
    public UserSecurity saveUser(UserSecurity request) {
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
            return new UserSecurity(user);
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
    public UserRequest getCurrentUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new UserRequest(user);
    }

    @Override
    public boolean isAdmin() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            for(Role role : user.getRoles()) {
                if(role.getName().equals(RoleEnum.ROLE_ADMIN.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isRoleEdit() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            for(Role role : user.getRoles()) {
                if(role.getName().equals(RoleEnum.ROLE_EDIT.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isRoleConfirm() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            for(Role role : user.getRoles()) {
                if(role.getName().equals(RoleEnum.ROLE_CONFIRM.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isRoleView() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            for(Role role : user.getRoles()) {
                if(role.getName().equals(RoleEnum.ROLE_VIEW.getName())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Integer getAccountType() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getAccountType() != null) {
            return user.getAccountType();
        }
        return null;
    }

    @Override
    public boolean hasHRIReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasHRIReport();
    }

    @Override
    public boolean hasATHReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasATHReport();
    }

    @Override
    public boolean hasMDReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasMDReport();
    }

    @Override
    public boolean hasARVReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasARVReport();
    }

    @Override
    public boolean hasCIReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasCIReport();
    }

    @Override
    public boolean hasMTCReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasMTCReport();
    }

    @Override
    public boolean hasCReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasCReport();
    }

    @Override
    public boolean hasPREPReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasPREPReport();
    }

    @Override
    public boolean hasPOCReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasPOCReport();
    }

    @Override
    public boolean hasCIHCReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasCIHCReport();
    }

    @Override
    public boolean hasRPREPReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasRPREPReport();
    }

    @Override
    public boolean hasHGReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasHGReport();
    }

    @Override
    public boolean hasSPReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasSPReport();
    }

    @Override
    public boolean hasICReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasICReport();
    }

    @Override
    public boolean hasHIReport() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getHasHIReport();
    }

}
