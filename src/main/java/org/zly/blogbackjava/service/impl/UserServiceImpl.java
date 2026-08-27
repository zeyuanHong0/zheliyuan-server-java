package org.zly.blogbackjava.service.impl;

import org.springframework.stereotype.Service;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.repository.UserRepository;
import org.zly.blogbackjava.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailWithOauths(email);
    }

    @Override
    public User findUserByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public void saveUser(User user) {
        userRepository.insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.updateUser(user);
    }
}
