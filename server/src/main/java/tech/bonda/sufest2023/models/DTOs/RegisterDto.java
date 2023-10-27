package tech.bonda.sufest2023.models.DTOs;

import lombok.Value;

import java.io.Serializable;

@Value
public class RegisterDto implements Serializable {
    String username;
    String name;
    String email;
    String password;
    String role;
}