package org.zly.blogbackjava.repository.impl;

import org.springframework.stereotype.Component;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.mapper.UserMapper;
import org.zly.blogbackjava.repository.UserRepository;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public User findByEmailWithOauths(String email) {
        return userMapper.findByEmailWithOauths(email);
    }

    @Override
    public User findByProviderAndProviderId(String provider, String providerId) {
        return userMapper.findByProviderAndProviderId(provider, providerId);
    }

    @Override
    public User findByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }
}
