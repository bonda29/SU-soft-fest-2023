package tech.bonda.sufest2023.models.DTOs;

import lombok.Value;

import java.io.Serializable;

@Value
public class ProductDto implements Serializable {
    String name;
    String description;
    Double price;
    int companyId;

}
