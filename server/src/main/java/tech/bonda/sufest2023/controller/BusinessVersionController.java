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

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto data) {
        return businessVersionControllerService.addProductToCompany(data);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Integer productId) {
        return businessVersionControllerService.getProduct(productId);
    }

    @GetMapping("/products/{companyId}")
    public ResponseEntity<?> getProductsByCompanyId(@PathVariable Integer companyId) {
        return businessVersionControllerService.getAllProductsOfCompany(companyId);
    }

    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts() {
        return businessVersionControllerService.getAllProducts();
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody ProductDto data) {
        return businessVersionControllerService.updateProduct(productId, data);
    }

    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        return businessVersionControllerService.deleteProduct(productId);
    }
}
