package com.jmaster.shop_app.enums.convert;

import com.jmaster.shop_app.enums.RoleCode;
import jakarta.persistence.AttributeConverter;

public class RoleCodeConverter implements AttributeConverter<RoleCode, String> {

    @Override
    public String convertToDatabaseColumn(RoleCode attribute) {
        if (attribute == null) {
            return null;
        }

        return attribute.getKey();
    }

    @Override
    public RoleCode convertToEntityAttribute(String dbData) {
        return RoleCode.fromKey(dbData);
    }
}
