package tech.bonda.sufest2023.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.sufest2023.models.DTOs.ProductDto;
import tech.bonda.sufest2023.services.BusinessVersionControllerService;

@RestController
@RequestMapping("/businessVersion")
@CrossOrigin("*")
@AllArgsConstructor
public class BusinessVersionController {
    private final BusinessVersionControllerService businessVersionControllerService;


    @GetMapping("/getProduct/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable Integer productId) {
        return businessVersionControllerService.getProduct(productId);
    }

    @GetMapping("/getAll/{companyId}")
    public ResponseEntity<?> getAllProductsOfCompany(@PathVariable Integer companyId) {
        return businessVersionControllerService.getAllProductsOfCompany(companyId);
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProductToCompany(@RequestBody ProductDto data) {
        return businessVersionControllerService.addProductToCompany(data);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        return businessVersionControllerService.deleteProduct(productId);
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody ProductDto data) {
        return businessVersionControllerService.updateProduct(productId, data);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts() {
        return businessVersionControllerService.getAllProducts();
    }

}
