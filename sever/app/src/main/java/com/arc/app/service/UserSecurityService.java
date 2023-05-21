package com.arc.app.service;

import com.arc.app.request.UserRequest;

public interface UserSecurityService {
    UserRequest saveUser(UserRequest request);
    Boolean duplicateUser(String username);
}
