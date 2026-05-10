package com.jmaster.shop_app.enums.convert;

import com.jmaster.shop_app.enums.UserStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class UserStatusConverter implements AttributeConverter<UserStatus, String> {


    @Override
    public String convertToDatabaseColumn(UserStatus userStatus) {
        if (userStatus == null) {
            return null;
        };
        return userStatus.getKey();
    }

    @Override
    public UserStatus convertToEntityAttribute(String s) {
        return UserStatus.fromKey(s);
    }
}
