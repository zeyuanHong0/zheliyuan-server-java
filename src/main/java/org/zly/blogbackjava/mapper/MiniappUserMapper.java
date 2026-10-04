package org.zly.blogbackjava.mapper;

import org.apache.ibatis.annotations.Param;
import org.zly.blogbackjava.entity.WxUser;

public interface MiniappUserMapper {
    WxUser findByOpenid(@Param("openid") String openid);

    WxUser findByIdAndOpenid(
            @Param("id") String id,
            @Param("openid") String openid
    );

    void insertMiniappUser(WxUser wxUser);
}
