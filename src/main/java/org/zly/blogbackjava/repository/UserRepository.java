package org.zly.blogbackjava.repository;

import org.zly.blogbackjava.entity.User;

public interface UserRepository {
    User findByEmailWithOauths(String email);
}
