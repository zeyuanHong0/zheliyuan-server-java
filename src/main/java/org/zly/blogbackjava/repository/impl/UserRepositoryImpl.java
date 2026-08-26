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
    public User findByEmailWithOauths(String email) {
        return userMapper.findByEmailWithOauths(email);
    }
}
