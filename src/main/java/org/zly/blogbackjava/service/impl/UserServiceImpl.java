package org.zly.blogbackjava.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.entity.UserOauth;
import org.zly.blogbackjava.enums.OAuthProvider;
import org.zly.blogbackjava.enums.Role;
import org.zly.blogbackjava.repository.UserRepository;
import org.zly.blogbackjava.repository.UserOauthRepository;
import org.zly.blogbackjava.service.UserService;
import org.zly.blogbackjava.util.IdGenerator;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserOauthRepository userOauthRepository;

    public UserServiceImpl(UserRepository userRepository, UserOauthRepository userOauthRepository) {
        this.userRepository = userRepository;
        this.userOauthRepository = userOauthRepository;
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

    @Override
    @Transactional
    public User createOauthUser(String name, String email, OAuthProvider provider, Long providerId) {
        User user = userRepository.findByProviderAndProviderId(provider.getCode(), String.valueOf(providerId));
        if (user != null) {
            return user;
        }

        user = userRepository.findByEmail(email);
        if (user == null) {
            user = new User();
            user.setId(IdGenerator.generateId());
            user.setUsername(name);
            user.setEmail(email);
            user.setAccountType(Role.TOURIST.getCode());
            userRepository.insertUser(user);
        }

        UserOauth userOauth = new UserOauth();
        userOauth.setId(IdGenerator.generateId());
        userOauth.setUserId(user.getId());
        userOauth.setProvider(provider.getCode());
        userOauth.setProviderId(String.valueOf(providerId));
        userOauthRepository.insertUserOauth(userOauth);

        return user;
    }
}
