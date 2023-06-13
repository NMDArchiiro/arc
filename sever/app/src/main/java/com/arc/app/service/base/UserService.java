package com.arc.app.service.base;

import com.arc.app.request.base.UserRequest;
import com.arc.app.request.search.SearchRequest;
import com.arc.app.response.ResponseObject;
import org.springframework.data.domain.Page;

public interface UserService {
    ResponseObject find(Long id);
    UserRequest saveUser(UserRequest request);
    ResponseObject changePassword(UserRequest request);
    Boolean delete(Long id);
    ResponseObject setLocked(Long id);
    Page<UserRequest> getPage(SearchRequest request);
}
