package org.zly.blogbackjava.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zly.blogbackjava.dto.user.request.ChangeUserStatusDto;
import org.zly.blogbackjava.dto.user.request.UpdateProfileDto;
import org.zly.blogbackjava.dto.user.request.UserListAdminDto;
import org.zly.blogbackjava.dto.user.response.UserListAdminResult;
import org.zly.blogbackjava.dto.user.response.UserListItem;
import org.zly.blogbackjava.dto.user.response.UserProfileDto;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.entity.UserOauth;
import org.zly.blogbackjava.enums.OAuthProvider;
import org.zly.blogbackjava.enums.Role;
import org.zly.blogbackjava.exception.BusinessException;
import org.zly.blogbackjava.repository.UserRepository;
import org.zly.blogbackjava.repository.UserOauthRepository;
import org.zly.blogbackjava.service.UserService;
import org.zly.blogbackjava.util.IdGenerator;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserOauthRepository userOauthRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailWithOauths(email);
    }

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id);
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

    @Override
    public UserProfileDto getProfile(String userId) {
        User user = userRepository.findById(userId);
        return new UserProfileDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getAccountType()
        );
    }

    @Override
    public boolean isAdmin(String userId) {
        User user = userRepository.findById(userId);
        return user != null && Role.ADMIN.getCode().equals(user.getAccountType());
    }

    @Override
    public void updateProfile(String userId, UpdateProfileDto updateProfileDto) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new BusinessException(404, "用户不存在");
        }
        String username = updateProfileDto.getUsername();
        String password = updateProfileDto.getPassword();
        String confirmPassword = updateProfileDto.getConfirmPassword();
        if (username != null && !username.equals(user.getUsername())) {
            User findUserName = userRepository.findByUserName(username);
            if (findUserName != null) throw new BusinessException(400, "用户名已存在");
        }
        if (password != null && !password.equals(user.getPassword())) {
            if (!password.equals(confirmPassword)) {
                throw new BusinessException(400, "两次输入的密码不一致");
            }
        }
        User updateUser = new User();
        updateUser.setId(userId);
        if (username != null) updateUser.setUsername(username);
        if (password != null) updateUser.setPassword(passwordEncoder.encode(password));
        userRepository.updateUser(updateUser);
    }

    @Override
    @Transactional
    public UserListAdminResult getUserList(UserListAdminDto query) {
        String name = query.getName();
        Integer pageNum = query.getPageNum();
        Integer pageSize = query.getPageSize();
        Integer offset = (pageNum - 1) * pageSize;
        List<UserListItem> list = userRepository.getUserList(name, offset, pageSize)
                .stream().map((user) -> new UserListItem(
                        user.getId(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getCreateTime(),
                        user.getUpdateTime(),
                        user.getIsDelete()
                )).toList();
        Long total = userRepository.countUserList(name);
        return new UserListAdminResult(list, total, pageNum, pageSize);
    }

    @Override
    public void changeUserStatus(ChangeUserStatusDto changeUserStatusDto) {
        String userId = changeUserStatusDto.getId();
        Integer status = changeUserStatusDto.getStatus();
        if (userId == null || userId.isBlank()) {
            throw new BusinessException(400, "用户 ID 不能为空");
        }
        if (status == null || (status != 0 && status != 1)) {
            throw new BusinessException(400, "用户状态只能为 0 或 1");
        }
        if (userRepository.findById(userId) == null) {
            throw new BusinessException(404, "用户不存在");
        }

        User user = new User();
        user.setId(userId);
        user.setIsDelete(status);
        userRepository.updateUser(user);
    }
}
