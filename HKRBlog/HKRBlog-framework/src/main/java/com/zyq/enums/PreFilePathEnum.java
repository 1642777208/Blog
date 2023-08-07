package com.zyq.enums;

import lombok.Getter;

@Getter
public enum PreFilePathEnum {

    OSS_CLIENT_IMG("client/avatar/");

    private final String path;

    PreFilePathEnum(String path) {
        this.path = path;
    }
}
