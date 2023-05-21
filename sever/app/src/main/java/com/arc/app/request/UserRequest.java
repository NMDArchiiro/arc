package com.arc.app.request;

import com.arc.app.entity.Role;
import com.arc.app.entity.User;
import com.arc.app.utils.constants.ValidationMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    private Long id;

    @Pattern(regexp = "[a-zA-Z0-9]+", message = ValidationMessage.USER_NAME)
    @NotNull(message = ValidationMessage.USER_NAME)
    private String username;

    @NotNull(message = ValidationMessage.NULL)
    private String password;

    @NotNull(message = ValidationMessage.NULL)
    private List<RoleRequest> roles = new ArrayList<>();

    public UserRequest(User entity) {
        if(!ObjectUtils.isEmpty(entity)) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            this.password = entity.getPassword();
            if(!CollectionUtils.isEmpty(entity.getRoles())) {
                for(Role role : entity.getRoles()) {
                    this.roles.add(new RoleRequest(role));
                }
            }
        }
    }
}
