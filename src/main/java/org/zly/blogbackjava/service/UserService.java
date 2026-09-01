package org.zly.blogbackjava.service;

import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.enums.OAuthProvider;

public interface UserService {
    User findUserByEmail(String email);

    User findUserById(String id);

    User findUserByUserName(String username);

    void saveUser(User user);

    void updateUser(User user);

    User createOauthUser(String name, String email, OAuthProvider provider, Long providerId);
}
