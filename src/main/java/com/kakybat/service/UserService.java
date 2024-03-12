package com.kakybat.service;

import com.kakybat.dto.UserDto;
import com.kakybat.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void deleteUser(User user);
    User findUserByEmail(String email);
    List<UserDto> findAllUsers();
}
