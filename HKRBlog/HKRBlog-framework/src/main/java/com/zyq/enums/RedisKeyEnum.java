package com.zyq.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RedisKeyEnum {

    CLIENT_LOGIN("clientLogin:");

    private final String key;
}
