package com.arc.app.service.base;

import com.arc.app.request.base.RoleRequest;
import com.arc.app.request.base.UserRequest;
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