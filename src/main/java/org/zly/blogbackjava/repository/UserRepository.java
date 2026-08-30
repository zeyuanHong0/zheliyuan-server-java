package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.entity.User;

public interface UserRepository {
    User findByEmail(String email);

    User findByEmailWithOauths(String email);

    User findByProviderAndProviderId(String provider, String providerId);

    User findByUserName(String username);

    void insertUser(User user);

    void updateUser(User user);
}
