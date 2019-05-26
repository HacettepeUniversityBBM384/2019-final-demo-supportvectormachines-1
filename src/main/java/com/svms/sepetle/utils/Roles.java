package com.svms.sepetle.utils;

public enum Roles {
    ROLE_ADMIN(1), ROLE_USER(2), ROLE_SELLER(3);
    private int value;

    Roles(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
