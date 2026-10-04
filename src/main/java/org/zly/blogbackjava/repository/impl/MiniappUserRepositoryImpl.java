package org.zly.blogbackjava.repository.impl;

import org.springframework.stereotype.Component;
import org.zly.blogbackjava.entity.WxUser;
import org.zly.blogbackjava.mapper.MiniappUserMapper;
import org.zly.blogbackjava.repository.MiniappUserRepository;

@Component
public class MiniappUserRepositoryImpl implements MiniappUserRepository {
    private final MiniappUserMapper miniappUserMapper;

    public MiniappUserRepositoryImpl(MiniappUserMapper miniappUserMapper) {
        this.miniappUserMapper = miniappUserMapper;
    }

    @Override
    public WxUser findByOpenid(String openid) {
        return miniappUserMapper.findByOpenid(openid);
    }

    @Override
    public WxUser findByIdAndOpenid(String id, String openid) {
        return miniappUserMapper.findByIdAndOpenid(id, openid);
    }

    @Override
    public void insertMiniappUser(WxUser wxUser) {
        miniappUserMapper.insertMiniappUser(wxUser);
    }
}
