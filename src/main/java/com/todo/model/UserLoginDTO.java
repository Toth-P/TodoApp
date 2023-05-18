package com.todo.model;

import com.todo.security.AuthenticationResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginDTO {

    private String status;
    private String message;
    private String token;

    public UserLoginDTO(AuthenticationResponse authenticationResponse) {
        this.status = "ok";
        this.token = authenticationResponse.getJwt();
    }

    public UserLoginDTO(String message) {
        this.status = "error";
        this.message = message;
    }
}