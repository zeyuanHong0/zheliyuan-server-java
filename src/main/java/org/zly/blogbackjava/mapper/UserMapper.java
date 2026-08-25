package org.zly.blogbackjava.mapper;

import org.zly.blogbackjava.entity.User;

public interface UserMapper {
    User findByEmail(String email);

    User findByEmailWithOauths(String email);
}
