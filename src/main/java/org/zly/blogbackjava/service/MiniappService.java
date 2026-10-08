package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.miniapp.response.MiniappLoginResult;
import org.zly.blogbackjava.entity.WxUser;

public interface MiniappService {

    MiniappLoginResult miniappLogin(String code);

    String refreshAccessToken(WxUser wxUser);

    boolean isUserAdmin(String openid);
}
