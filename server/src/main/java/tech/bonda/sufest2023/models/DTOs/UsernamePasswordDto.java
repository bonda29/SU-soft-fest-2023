package tech.bonda.sufest2023.models.DTOs;

import lombok.Value;
import tech.bonda.sufest2023.models.User;

import java.io.Serializable;

/**
 * DTO for Login Credentials of {@link User}
 */
@Value
public class UsernamePasswordDto implements Serializable {
    String username;
    String password;
}
