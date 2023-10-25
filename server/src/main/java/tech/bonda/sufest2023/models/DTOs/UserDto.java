package tech.bonda.sufest2023.models.DTOs;

import lombok.Value;
import tech.bonda.sufest2023.models.User;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
@Value
public class UserDto implements Serializable {
    String username;
    String password;
    String email;
    String phone;
    String date_of_registration;
}