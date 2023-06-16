package com.arc.app.request.dto;

import com.arc.app.entity.base.AdminUnit;
import com.arc.app.request.base.AdminUnitRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * author: NMDuc
 **/
@NoArgsConstructor
@Getter
@Setter
public class AdminUnitDto {
    private Long id;
    private String name;
    private Integer level;
    private List<Object> children;

    public AdminUnitDto(AdminUnit entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.level = entity.getLevel();
            if (!CollectionUtils.isEmpty(entity.getChildrens())) {
                List<AdminUnitDto> childrens = new ArrayList<>();
                for (AdminUnit child : entity.getChildrens()) {
                    childrens.add(new AdminUnitDto(child));
                }
                this.children = new ArrayList<>(childrens);
            }
        }
    }

    public AdminUnitDto(AdminUnitRequest entity, boolean simple) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.level = entity.getLevel();
            if (!CollectionUtils.isEmpty(entity.getChildren()) && !simple) {
                List<AdminUnitDto> childrens = new ArrayList<>();
                for (AdminUnitRequest child : entity.getChildren()) {
                    childrens.add(new AdminUnitDto(child,true));
                }
                this.children = new ArrayList<>(childrens);
            }
        }
    }

    public AdminUnitDto(AdminUnit entity,boolean simple) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.level = entity.getLevel();
        }
    }
}
