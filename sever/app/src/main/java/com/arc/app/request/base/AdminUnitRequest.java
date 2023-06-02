package com.arc.app.request.base;

import com.arc.app.entity.base.AdminUnit;
import com.arc.app.utils.constants.ValidationMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class AdminUnitRequest {
    private Long id;

    @NotNull(message = ValidationMessage.NULL)
    private String code;

    private String name;

    private Integer level;

    private AdminUnitRequest parent;

    private List<AdminUnitRequest> children;

    public AdminUnitRequest(AdminUnit entity, boolean isGetChildren) {
        if(entity != null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.level = entity.getLevel();
            if(entity.getParent() != null) {
                this.parent = new AdminUnitRequest(entity.getParent(), false);
            }
            if(isGetChildren && !CollectionUtils.isEmpty(entity.getChildrens())) {
                for(AdminUnit children : entity.getChildrens()) {
                    this.children.add(new AdminUnitRequest(children, false));
                }
            }
        }
    }
}
