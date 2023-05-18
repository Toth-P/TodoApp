package com.todo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;


    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Valid
    private List<@Valid Todo> todos;

    public User(String username) {
        this.username = username;
    }

    public User(RegisterRequestDTO user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

}

