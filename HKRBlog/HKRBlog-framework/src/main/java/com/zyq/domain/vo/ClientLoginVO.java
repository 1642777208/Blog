package com.zyq.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ClientLoginVO {
    private String token;
    private UserInfoVO userInfoVO;
}
