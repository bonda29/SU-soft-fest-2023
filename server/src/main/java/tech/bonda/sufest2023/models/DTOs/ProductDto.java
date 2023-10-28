package tech.bonda.sufest2023.models.DTOs;

import lombok.Value;
import tech.bonda.sufest2023.models.AppProduct;

import java.io.Serializable;

/**
 * DTO for {@link AppProduct}
 */
@Value
public class ProductDto implements Serializable {
    String name;
    String description;
    Double price;
    String image;
    int companyId;

}
