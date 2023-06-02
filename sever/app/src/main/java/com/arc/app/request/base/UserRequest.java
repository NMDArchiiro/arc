package com.arc.app.request.base;

import com.arc.app.entity.base.Role;
import com.arc.app.entity.base.User;
import com.arc.app.utils.constants.ValidationMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

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
    private String username;
    @NotNull(message = ValidationMessage.NULL)
    private String password;
    private String confirmPassword;
    @NotNull(message = ValidationMessage.NULL)
    private List<RoleRequest> roles = new ArrayList<>();
    private String displayName;
    private String email;
    private Integer accountType;
    private Boolean active;
    private Boolean hasHRIReport = false;
    private Boolean hasATHReport = false;
    private Boolean hasPOCReport = false;
    private Boolean hasMDReport = false;
    private Boolean hasARVReport = false;
    private Boolean hasCIReport = false;
    private Boolean hasMTCReport = false;
    private Boolean hasPREPReport = false;
    private Boolean hasCReport = false;
    private Boolean hasRPREPReport = false;
    private Boolean hasCIHCReport = false;
    private Boolean hasHGReport = false;
    private Boolean hasSPReport = false;
    private Boolean hasICReport = false;
    private Boolean hasHIReport = false;
    private HealthOrganizationRequest healthOrganization;
    private AdminUnitRequest province;
    private AdminUnitRequest district;
    private AdminUnitRequest commune;

    public UserRequest(User entity) {
        if(entity != null) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            if(!CollectionUtils.isEmpty(entity.getRoles())) {
                for(Role role : entity.getRoles()) {
                    this.roles.add(new RoleRequest(role));
                }
            }
            this.displayName = entity.getDisplayName();
            this.email = entity.getEmail();
            this.accountType = entity.getAccountType();
            this.active = entity.getActive();
            this.hasHRIReport = entity.getHasHRIReport();
            this.hasATHReport = entity.getHasATHReport();
            this.hasPOCReport = entity.getHasPOCReport();
            this.hasMDReport = entity.getHasMDReport();
            this.hasARVReport = entity.getHasARVReport();
            this.hasCIReport = entity.getHasCIReport();
            this.hasMTCReport = entity.getHasMTCReport();
            this.hasPREPReport = entity.getHasPREPReport();
            this.hasCReport = entity.getHasCReport();
            this.hasRPREPReport = entity.getHasRPREPReport();
            this.hasCIHCReport = entity.getHasCIHCReport();
            this.hasHGReport = entity.getHasHGReport();
            this.hasSPReport = entity.getHasSPReport();
            this.hasICReport = entity.getHasICReport();
            this.hasHIReport = entity.getHasHIReport();
            if(entity.getHealthOrganization()!= null) {
                this.healthOrganization = new HealthOrganizationRequest(entity.getHealthOrganization(), false);
            }
            if(entity.getProvince()!= null) {
                this.province = new AdminUnitRequest(entity.getProvince(), false);
            }
            if(entity.getDistrict()!= null) {
                this.district = new AdminUnitRequest(entity.getDistrict(), false);
            }
            if(entity.getCommune()!= null) {
                this.commune = new AdminUnitRequest(entity.getCommune(), false);
            }
        }
    }

}
