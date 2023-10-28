package tech.bonda.sufest2023.models.DTOs;

import lombok.Value;

import java.io.Serializable;

@Value
public class ForgotPasswordDTO implements Serializable {
    String email;
    String passwordSendToEmail;
    String newPassword;
}
