package org.zly.blogbackjava.repository.impl;

import org.springframework.stereotype.Component;
import org.zly.blogbackjava.entity.UserOauth;
import org.zly.blogbackjava.mapper.UserOauthMapper;
import org.zly.blogbackjava.repository.UserOauthRepository;

@Component
public class UserOauthRepositoryImpl implements UserOauthRepository {
    private final UserOauthMapper userOauthMapper;

    public UserOauthRepositoryImpl(UserOauthMapper userOauthMapper) {
        this.userOauthMapper = userOauthMapper;
    }

    @Override
    public void insertUserOauth(UserOauth userOauth) {
        userOauthMapper.insertUserOauth(userOauth);
    }
}
