package com.todo.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterResponseDTO {
    private String status;
    private String message;
    private Long id;
    private String username;


    public RegisterResponseDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public RegisterResponseDTO(String message) {
        this.message = message;
        status = "error";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegisterResponseDTO)) {
            return false;
        }
        RegisterResponseDTO that = (RegisterResponseDTO) o;
        return getId().equals(that.getId()) && getUsername().equals(that.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername());
    }
}