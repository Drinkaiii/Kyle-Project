package com.example.service.user;

import com.example.dto.user.UserDto;
import com.example.mapper.user.UserMapper;
import com.example.response.ErrorResponse;
import com.example.response.user.UserApiResponse;
import com.example.response.user.UserAuthResponse;
import com.example.response.user.UserResponse;
import com.example.util.JwtUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class UserServiceImp implements UserService {

    @Value("${jwt.duration}")
    private int duration;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Object signup(UserDto userDto) {
        try {
            if (userDto == null)
                new ResponseEntity<>(new ErrorResponse("Miss sign up information"), HttpStatus.BAD_REQUEST);
            if (userDto.getEmail() == null || userDto.getPassword() == null)
                return new ResponseEntity<>(new ErrorResponse("Invalid register information"), HttpStatus.BAD_REQUEST);
            if (userMapper.getUserByEmail(userDto.getEmail()) == null) {
                // hash the password
                userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
                // save the user information to database
                userDto.setProvider("native");
                userDto.setPicture("http://test.picture");
                userDto = userMapper.setUser(userDto);
                // generate JWT
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("id", userDto.getId());
                userMap.put("provider", userDto.getProvider());
                userMap.put("name", userDto.getName());
                userMap.put("email", userDto.getEmail());
                userMap.put("picture", userDto.getPicture());
                String token = jwtUtil.getToken(userMap);
                // generate the response
                UserAuthResponse userAuthResponse = new UserAuthResponse();
                userAuthResponse.setAccessToken(token);
                userAuthResponse.setAccessExpired(duration / 1000);
                userAuthResponse.setUser(generateUserResponse(userDto, true));
                // send the response
                return new UserApiResponse(userAuthResponse);

            } else
                return new ResponseEntity<>(new ErrorResponse("This email address is already in use"), HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Server Error Response"), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public Object signin(UserDto userDto) {
        try {
            if (userDto == null)
                return new ResponseEntity<>(new ErrorResponse("Miss sign in information"), HttpStatus.BAD_REQUEST);
            if ("facebook".equals(userDto.getProvider()) && userDto.getAccessToken() != null) {
                // verify the token
                try {
                    // use facebook api
                    String url = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + userDto.getAccessToken();
                    RestTemplate restTemplate = new RestTemplate();
                    String resultJson = restTemplate.getForObject(url, String.class);
                    // parse JSON String
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> userMap = objectMapper.readValue(resultJson, new TypeReference<Map<String, Object>>() {
                    });
                    // generate JWT
                    userMap.put("provider", userDto.getProvider());
                    userMap.put("picture", "default picture url");
                    String token = jwtUtil.getToken(userMap);
                    //save user information to database
                    userDto.setName(userMap.get("name").toString());
                    userDto.setEmail(userMap.get("email").toString());
                    userDto.setPassword("Facebook Authorization");
                    userDto.setPicture(userMap.get("picture").toString());
                    userDto.setProvider(userMap.get("provider").toString());
                    if (userMapper.getUserByEmail(userDto.getEmail()) == null)
                        userMapper.setUser(userDto);
                    else
                        userDto.setId(userMapper.getUserByEmail(userDto.getEmail()).getId());
                    // generate the response
                    UserAuthResponse userAuthResponse = new UserAuthResponse();
                    userAuthResponse.setAccessToken(token);
                    userAuthResponse.setAccessExpired(duration / 1000);
                    userAuthResponse.setUser(generateUserResponse(userDto, true));
                    // send the response
                    return new UserApiResponse(userAuthResponse);
                } catch (Exception e) {
                    log.warn(e);
                    return new ResponseEntity<>(new ErrorResponse("Token is wrong"), HttpStatus.FORBIDDEN);
                }

            }

            if (userDto.getEmail() == null || userDto.getPassword() == null)
                return new ResponseEntity<>(new ErrorResponse("Invalid username or password"), HttpStatus.FORBIDDEN);
            else
                try {
                    // create authentication toekn
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
                    // verify
                    Authentication authentication = authenticationManager.authenticate(authenticationToken);
                    // set user information to Security Context
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    // generate JWT
                    userDto = userMapper.getUserByEmail(userDto.getEmail());
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("id", userDto.getId());
                    userMap.put("provider", userDto.getProvider());
                    userMap.put("name", userDto.getName());
                    userMap.put("email", userDto.getEmail());
                    userMap.put("picture", userDto.getPicture());
                    String token = jwtUtil.getToken(userMap);
                    // generate the response
                    UserAuthResponse userAuthResponse = new UserAuthResponse();
                    userAuthResponse.setAccessToken(token);
                    userAuthResponse.setAccessExpired(duration / 1000);
                    userAuthResponse.setUser(generateUserResponse(userDto, true));
                    // send the response
                    return new UserApiResponse(userAuthResponse);
                } catch (AuthenticationException e) {
                    // response error
                    return new ResponseEntity<>(new ErrorResponse("The username or password is incorrect."), HttpStatus.FORBIDDEN);
                }

        } catch (Exception e) {
            log.warn(e);
            return new ResponseEntity<>(new ErrorResponse("Server Error Response"), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public Object showUserProfile(UserDto userDto) {
        return new UserApiResponse(generateUserResponse(userDto, false));
    }

    private UserResponse generateUserResponse(UserDto userDto, boolean isIncludeId) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userDto.getId());
        userResponse.setProvider(userDto.getProvider());
        userResponse.setName(userDto.getName());
        userResponse.setEmail(userDto.getEmail());
        userResponse.setPicture(userDto.getPicture());
        userResponse.setIncludeId(isIncludeId);
        return userResponse;
    }

}
