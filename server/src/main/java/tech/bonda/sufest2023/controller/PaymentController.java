package tech.bonda.sufest2023.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.sufest2023.services.Coinbase.CoinbaseCheckoutService;
import tech.bonda.sufest2023.services.Stripe.StripeProductCreationService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payment")
@CrossOrigin("*")
@AllArgsConstructor
public class PaymentController {
    private final StripeProductCreationService stripeProductCreationService;
    private final CoinbaseCheckoutService coinbaseCheckoutService;
    @PostMapping("/stripe")
    public ResponseEntity<?> test(@RequestBody List<String> data) {

        List<Integer> intIds = data.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        return stripeProductCreationService.getStripeUrl(intIds);
    }

    @PostMapping("/coinbase")
    public ResponseEntity<?> test2(@RequestBody List<String> data) {

        List<Integer> intIds = data.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        try
        {
            return coinbaseCheckoutService.createCheckout(intIds);
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }


}
