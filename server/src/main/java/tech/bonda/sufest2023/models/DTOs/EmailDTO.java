package tech.bonda.sufest2023.models.DTOs;

import lombok.Value;

import java.io.Serializable;

@Value
public class EmailDTO implements Serializable {
    String to;
    String name;
    String subject;
    String content;

}
