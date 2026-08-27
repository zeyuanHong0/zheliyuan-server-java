package org.zly.blogbackjava.service;

import org.zly.blogbackjava.entity.User;

public interface UserService {
    User findUserByEmail(String email);

    User findUserByUserName(String username);

    void saveUser(User user);

    void updateUser(User user);
}
