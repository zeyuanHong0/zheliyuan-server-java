package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.dto.request.UserListAdminDto;
import org.zly.blogbackjava.entity.User;

import java.util.List;

public interface UserRepository {
    User findByEmail(String email);

    User findById(String id);

    User findByEmailWithOauths(String email);

    User findByProviderAndProviderId(String provider, String providerId);

    User findByUserName(String username);

    void insertUser(User user);

    void updateUser(User user);

    List<User> getUserList(String name, Integer offset, Integer pageSize);

    Long countUserList(String name);
}
