package org.zly.blogbackjava.service;

import org.zly.blogbackjava.dto.user.request.ChangeUserStatusDto;
import org.zly.blogbackjava.dto.user.request.UpdateProfileDto;
import org.zly.blogbackjava.dto.user.request.UserListAdminDto;
import org.zly.blogbackjava.dto.user.response.UserListAdminResult;
import org.zly.blogbackjava.entity.User;
import org.zly.blogbackjava.dto.user.response.UserProfileDto;
import org.zly.blogbackjava.enums.OAuthProvider;

public interface UserService {
    User findUserByEmail(String email);

    User findUserById(String id);

    User findUserByUserName(String username);

    void saveUser(User user);

    void updateUser(User user);

    User createOauthUser(String name, String email, OAuthProvider provider, Long providerId);

    UserProfileDto getProfile(String userId);

    boolean isAdmin(String userId);

    void updateProfile(String userId, UpdateProfileDto updateProfileDto);

    UserListAdminResult getUserList(UserListAdminDto query);

    void changeUserStatus(ChangeUserStatusDto changeUserStatusDto);
}
