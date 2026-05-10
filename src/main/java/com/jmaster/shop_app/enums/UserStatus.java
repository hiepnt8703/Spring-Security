package com.jmaster.shop_app.enums;

public enum UserStatus {
    ACTIVE("ACTIVE", "Đang hoạt động"),
    LOCKED("LOCKED", "Đã khóa");

    private final String key;
    private final String value;

    UserStatus(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static UserStatus fromKey(String key) {
        if (key == null) {
            return null;
        }

        for (UserStatus status : UserStatus.values()) {
            if (status.getKey().equals(key)) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid UserStatus key: " + key);
    }
}
