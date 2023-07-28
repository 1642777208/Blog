package com.zyq.blog.demo1.model.enums;

public enum AdminEnum {
    ADMIN("admin");

    AdminEnum(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }

    public static Boolean isAdmin(String username) {
        boolean include = false;
        for (AdminEnum e : AdminEnum.values()) {
            if (e.getValue().equals(username)) {
                include = true;
                break;
            }
        }
        return include;
    }
}
