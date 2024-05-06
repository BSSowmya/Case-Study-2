package com.javamaster.user.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank(message = "Name cannot be empty")
    @Column(name = "name")
    private String username;

    @ApiModelProperty(example = "1b$lpeVBc:BN'GUCcO_MF'0>xrEe&tf+%d'6vN")
    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$", message = "Password must be min 8  characters with atleast one uppercase, lowercase, digit and special character")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private ERole role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = ERole.valueOf(role);
    }
}
