package com.example.service.user;

import com.example.dto.user.UserDto;

public interface UserService {

    Object signup(UserDto userDto);

    Object signin(UserDto userDto);

    Object showUserProfile(UserDto userDto);

}
