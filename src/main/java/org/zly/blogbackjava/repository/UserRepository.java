package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.entity.User;

public interface UserRepository {
    User findByEmailWithOauths(String email);

    User findByUserName(String username);

    void insertUser(User user);

    void updateUser(User user);
}
