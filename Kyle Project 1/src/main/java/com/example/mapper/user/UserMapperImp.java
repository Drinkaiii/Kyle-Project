package com.example.mapper.user;

import com.example.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserMapperImp implements UserMapper {

    @Value("${spring.datasource.url}")
    String urlSQL;
    @Value("${spring.datasource.username}")
    String usernameSQL;
    @Value("${spring.datasource.password}")
    String passwordSQL;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public UserDto setUser(UserDto userDto) {
        String sql = "INSERT INTO user (name, email, password, picture, provider) VALUES (:name, :email, :password, :picture, :provider)";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        KeyHolder keyHolder = new GeneratedKeyHolder();

        parameters.addValue("name", userDto.getName());
        parameters.addValue("email", userDto.getEmail());
        parameters.addValue("password", userDto.getPassword());
        parameters.addValue("picture", userDto.getPicture());
        parameters.addValue("provider", userDto.getProvider());
        namedParameterJdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"});
        userDto.setId(keyHolder.getKey().intValue());
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        String sql = "SELECT * FROM user WHERE email = :email";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("email", email);
        List<UserDto> userDtos = namedParameterJdbcTemplate.query(sql, parameters, (RowMapper<UserDto>) (rs, rowNum) -> {
            UserDto userDto = new UserDto();
            userDto.setId(rs.getInt("id"));
            userDto.setProvider(rs.getString("provider"));
            userDto.setName(rs.getString("name"));
            userDto.setEmail(rs.getString("email"));
            userDto.setPassword(rs.getString("password"));
            userDto.setPicture(rs.getString("picture"));
            return userDto;
        });
        if (userDtos.size() > 0)
            return userDtos.get(0);
        else
            return null;
    }
}
