package tech.bonda.sufest2023.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.sufest2023.models.DTOs.ProductDto;
import tech.bonda.sufest2023.services.Stripe.StripeProductCreationService;

import java.util.List;
import java.util.stream.Collectors;

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

//    @PostMapping("/createSession")
//    public ResponseEntity<?> createSession(@RequestBody List<Integer> data) {
//        return stripeProductCreationService.getStripeUrl(data);
//    }

    @PostMapping("/payment")
    public List<String> test(@RequestBody List<String> data) {

        List<Integer> intIds = data.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return stripeProductCreationService.dbIdToStripePriceId(intIds);

    }

}
