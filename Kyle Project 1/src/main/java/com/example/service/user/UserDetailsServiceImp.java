package com.example.service.user;

import com.example.dto.user.UserDto;
import com.example.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // search the user
        UserDto userDto = userMapper.getUserByEmail(email);
        if (userDto == null)
            throw new UsernameNotFoundException(email);

        // create the default role authority
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        // make UserdTO to org.springframework.security.core.userdetails.User
        return org.springframework.security.core.userdetails.User.builder()
                .username(userDto.getEmail())
                .password(userDto.getPassword())
                .authorities(Collections.singletonList(authority))
                .build();
    }
}
