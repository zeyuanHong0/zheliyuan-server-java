package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.entity.WxUser;

public interface MiniappUserRepository {
    WxUser findByOpenid(String openid);

    WxUser findByIdAndOpenid(String id, String openid);

    void insertMiniappUser(WxUser wxUser);
}
