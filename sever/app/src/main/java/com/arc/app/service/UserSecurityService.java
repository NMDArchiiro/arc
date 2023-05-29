package com.arc.app.service;

import com.arc.app.request.UserRequest;
import com.arc.app.request.UserSecurity;

public interface UserSecurityService {
    UserSecurity saveUser(UserSecurity request);
    Boolean duplicateUser(String username);
    UserRequest getCurrentUser();
    boolean isAdmin();
    boolean isRoleEdit();
    boolean isRoleConfirm();
    boolean isRoleView();
    Integer getAccountType();
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
}
