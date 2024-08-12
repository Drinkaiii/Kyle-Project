package com.example.controller;

import com.example.dto.user.UserDto;
import com.example.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/1.0/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Object signup(@RequestBody(required = false) UserDto userDto) {
        return userService.signup(userDto);
    }

    @PostMapping("/signin")
    public Object signin(@RequestBody(required = false) UserDto userDto) {
        return userService.signin(userDto);
    }

    @GetMapping("/profile")
    public Object profile(@AuthenticationPrincipal UserDto userDto) {
        return userService.showUserProfile(userDto);
    }

}
