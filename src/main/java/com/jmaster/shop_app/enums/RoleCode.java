package com.jmaster.shop_app.enums;

public enum RoleCode {
    ADMIN("ADMIN", "Quản trị viên"),
    HR_LEAD("HR_LEAD", "Trưởng phòng nhân sự"),
    VIEWER("VIEWER", "Người xem");

    private final String key;
    private final String value;

    RoleCode(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static RoleCode fromKey(String key) {
        if (key == null) {
            return null;
        }

        for (RoleCode roleCode : RoleCode.values()) {
            if (roleCode.getKey().equals(key)) {
                return roleCode;
            }
        }

        throw new IllegalArgumentException("Invalid RoleCode key: " + key);
    }
}
