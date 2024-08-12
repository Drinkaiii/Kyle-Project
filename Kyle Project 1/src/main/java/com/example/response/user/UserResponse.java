package com.example.response.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int id;
    private String provider;
    private String name;
    private String email;
    private String picture;

    @JsonIgnore
    private boolean includeId;

    public int getId() {
        return includeId ? id : 0;
    }

}
