package org.zly.blogbackjava.mapper;

import org.apache.ibatis.annotations.Param;
import org.zly.blogbackjava.entity.User;

import java.util.List;

public interface UserMapper {
    User findByEmail(String email);

    User findById(String id);

    User findByEmailWithOauths(String email);

    User findByProviderAndProviderId(String provider, String providerId);

    User findByUserName(String username);

    void insertUser(User user);

    void updateUser(User user);

    List<User> getUserList(
            @Param("name") String name,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    Long countUserList(@Param("name") String name);
}
