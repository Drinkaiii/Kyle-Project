package com.example.mapper.user;

import com.example.dto.user.UserDto;

public interface UserMapper {

    UserDto setUser(UserDto userDto);

    UserDto getUserByEmail(String email);

}
