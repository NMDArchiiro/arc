package com.arc.app.request.base;

import com.arc.app.entity.base.AdminUnitHealthOrg;
import com.arc.app.entity.base.HealthOrg;
import com.arc.app.utils.constants.ValidationMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HealthOrgRequest {
    private Long id;
    @NotNull(message = ValidationMessage.NULL)
    private String code;
    private String name;
    private Integer level;
    private AdminUnitRequest province;
    private AdminUnitRequest district;
    private AdminUnitRequest commune;
    private HealthOrgRequest parent;
    private List<HealthOrgRequest> children;
    private List<AdminUnitRequest> listAdminUnit = new ArrayList<>();

    public HealthOrgRequest(HealthOrg entity, boolean isGetChildren) {
        if (entity != null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.level = entity.getLevel();
            if (entity.getCommune() != null) {
                // Xa
                AdminUnitRequest commune = new AdminUnitRequest();
                commune.setId(entity.getCommune().getId());
                commune.setCode(entity.getCommune().getCode());
                commune.setName(entity.getCommune().getName());
                this.commune = commune;
            }
            if (entity.getDistrict() != null) {
                // Huyen
                AdminUnitRequest district = new AdminUnitRequest();
                district.setId(entity.getDistrict().getId());
                district.setCode(entity.getDistrict().getCode());
                district.setName(entity.getDistrict().getName());
                this.district = district;
            }
            if (entity.getProvince() != null) {
                // Tinh
                AdminUnitRequest province = new AdminUnitRequest();
                province.setId(entity.getProvince().getId());
                province.setCode(entity.getProvince().getCode());
                province.setName(entity.getProvince().getName());
                this.province = province;
            }
            if (entity.getParent() != null) {
                this.parent = new HealthOrgRequest(entity.getParent(), false);
            }
            if (isGetChildren && !CollectionUtils.isEmpty(entity.getChildrens())) {
                for (HealthOrg children : entity.getChildrens()) {
                    this.children.add(new HealthOrgRequest(children, false));
                }
            }
            if (!CollectionUtils.isEmpty(entity.getListAdminUnit())) {
                for (AdminUnitHealthOrg adminUnitHealthOrg : entity.getListAdminUnit()) {
                    if (adminUnitHealthOrg.getAdminUnit() != null) {
                        this.listAdminUnit.add(new AdminUnitRequest(adminUnitHealthOrg.getAdminUnit(), false));
                    }
                }

            }
        }
    }
}
