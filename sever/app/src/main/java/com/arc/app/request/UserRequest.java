package com.arc.app.request;

import com.arc.app.utils.constants.ValidationMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@Setter
public class UserRequest {
    @Pattern(regexp = "[a-zA-Z0-9]+", message = ValidationMessage.USER_NAME)
    @NotNull(message = ValidationMessage.USER_NAME)
    private String username;

    @NotNull(message = ValidationMessage.NULL)
    private String password;

    @NotNull(message = ValidationMessage.NULL)
    private Set<RoleRequest> roles;

}
