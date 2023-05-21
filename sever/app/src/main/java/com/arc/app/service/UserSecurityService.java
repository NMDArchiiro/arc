package com.arc.app.service;

import com.arc.app.request.UserRequest;
import com.arc.app.response.UserResponse;

public interface UserSecurityService {
    UserRequest saveUser(UserRequest request);
    Boolean duplicateUser(String username);
    UserResponse getCurrentUser();
    boolean isAdmin();
    boolean hasHRIReport();
    boolean hasATHReport();
    boolean hasMDReport();
    boolean hasARVReport();
    boolean hasCIReport();
    boolean hasMTCReport();
    boolean hasCReport();
    boolean hasPREPReport();
    boolean hasPOCReport();
    boolean hasCIHCReport();
    boolean hasRPREPReport();
    boolean hasHGReport();
    boolean hasSPReport();
    boolean hasICReport();
    boolean hasHIReport();
    boolean isRoleView();
    boolean isRoleEdit();
    boolean isRoleConfirm();
    Integer getAccountType();

}
