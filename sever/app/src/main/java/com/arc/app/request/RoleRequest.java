package com.arc.app.request;

import com.arc.app.utils.constants.ValidationMessage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RoleRequest {
    private Long id;

    @NotNull(message = ValidationMessage.NULL)
    private String name;
}
