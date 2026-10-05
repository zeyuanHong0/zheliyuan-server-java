package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.miniapp.response.MiniappLoginResult;

public interface MiniappService {

    MiniappLoginResult miniappLogin(String code);
}
