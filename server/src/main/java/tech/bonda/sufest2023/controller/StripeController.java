package tech.bonda.sufest2023.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.sufest2023.models.DTOs.ProductDto;
import tech.bonda.sufest2023.services.Stripe.StripeProductCreationService;

import java.util.List;

@RestController
@RequestMapping("/stripe")
@CrossOrigin("*")
@AllArgsConstructor
public class StripeController {
    private final StripeProductCreationService stripeProductCreationService;

/*    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody ProductDto data) {
        return stripeProductCreationService.createProduct(data);
    }*/

    @PostMapping("/createSession")
    public ResponseEntity<?> createSession() {
        List<String> productIds = List.of("price_1O5xxRJBrDD3W9P8kErohdMj");
        return stripeProductCreationService.createSession(productIds);
    }

}
