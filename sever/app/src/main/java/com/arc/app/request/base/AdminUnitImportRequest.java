package com.arc.app.request.base;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

@Getter
@Setter
public class AdminUnitImportRequest {
    private String nameProvince;
    private String codeProvince;
    private String nameDistrict;
    private String codeDistrict;
    private String nameCommune;
    private String codeCommune;

    public boolean objectIsNew() throws IllegalAccessException {
        for(Field field : getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if(field.get(this) != null) {
                return false;
            }
        }
        return true;
    }
}
