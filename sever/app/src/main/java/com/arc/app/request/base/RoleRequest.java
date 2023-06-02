package com.arc.app.request.base;

import com.arc.app.entity.base.Role;
import com.arc.app.utils.constants.ValidationMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class RoleRequest {
    private Long id;

    @NotNull(message = ValidationMessage.NULL)
    private String name;

    public RoleRequest(Role entity) {
        this.id = entity.getId();
        this.name = entity.getName();
    }
}
