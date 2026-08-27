package org.zly.blogbackjava.mapper;

import org.zly.blogbackjava.entity.User;

public interface UserMapper {
    User findByEmail(String email);

    User findByEmailWithOauths(String email);

    User findByUserName(String username);

    void insertUser(User user);

    void updateUser(User user);
}
