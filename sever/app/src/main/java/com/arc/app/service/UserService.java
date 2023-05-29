package com.arc.app.service;

import com.arc.app.request.RoleRequest;
import com.arc.app.request.UserRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<RoleRequest> getAllRoles();
    ResponseObject find(Long id);
    ResponseObject saveUser(UserRequest request);
    Boolean delete(Long id);
    ResponseObject setLocked(Long id);
    Page<UserRequest> getPage(SearchRequest request);
}
