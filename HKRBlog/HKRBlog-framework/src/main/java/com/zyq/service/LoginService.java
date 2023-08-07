package com.zyq.service;

import com.zyq.domain.dto.LoginDTO;
import com.zyq.domain.vo.BaseResult;
import com.zyq.domain.vo.ClientLoginVO;
import org.springframework.stereotype.Service;

@Service
public interface LoginService {
    public BaseResult login (LoginDTO loginDTO);
    public BaseResult logout ();
}
