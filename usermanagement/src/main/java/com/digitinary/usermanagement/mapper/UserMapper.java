package com.digitinary.usermanagement.mapper;

import com.digitinary.usermanagement.entity.User;
import com.digitinary.usermanagement.model.UserModel;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User toUser(UserModel userModel) {
        return User.builder()
                .name(userModel.getName())
                .phoneNumber(userModel.getPhoneNumber())
                .build();
    }

    public UserModel toUserModel(User user) {
        return UserModel.builder()
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
