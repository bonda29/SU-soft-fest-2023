package tech.bonda.sufest2023.services;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bonda.sufest2023.models.DTOs.ProductDto;
import tech.bonda.sufest2023.models.Product;
import tech.bonda.sufest2023.repository.CompanyRepo;
import tech.bonda.sufest2023.repository.ProductRepo;
import tech.bonda.sufest2023.services.Stripe.StripeProductCreationService;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BusinessVersionControllerService {

    private final CompanyRepo companyRepo;

    private final ProductRepo productRepo;

    private final StripeProductCreationService stripeProductCreationService;


    //add product to company
    public ResponseEntity<?> addProductToCompany(ProductDto data) {
        if (companyRepo.findById(data.getCompanyId()).isPresent())
        {
            //company exists
            //check if product exists
            if (productRepo.findByName(data.getName()).isPresent())
            {
                return ResponseEntity.badRequest().body("Product with this name already exists");
            }
            else
            {
                //product does not exist
                Product product = Product.builder()
                        .name(data.getName())
                        .description(data.getDescription())
                        .price(data.getPrice())
                        .company(companyRepo.findById(data.getCompanyId()).get())
                        .image(data.getImage())
                        .build();
                //create product in stripe
                String s =  stripeProductCreationService.createProduct(data);

                product.setStripeId(s);
                return ResponseEntity.ok(productRepo.save(product));
            }
        }
        else
        {
            //company does not exist
            return ResponseEntity.notFound().build();
        }
    }

    //get all products of company
    public ResponseEntity<?> getAllProductsOfCompany(Integer companyId) {
        if (companyRepo.findById(companyId).isPresent())
        {
            //company exists
            return ResponseEntity.ok(productRepo.findAllByCompanyId(companyId));
        }
        else
        {
            //company does not exist
            return ResponseEntity.notFound().build();
        }
    }

    //get product
    public ResponseEntity<?> getProduct(Integer productId) {
        if (productRepo.findById(productId).isPresent())
        {
            //product exists
            return ResponseEntity.ok(productRepo.findById(productId));
        }
        else
        {
            //product does not exist
            return ResponseEntity.notFound().build();
        }
    }

    // Update product
    public ResponseEntity<?> updateProduct(Integer productId, ProductDto data) {
        // Check if the product with the given productId exists
        Optional<Product> existingProduct = productRepo.findById(productId);
        if (existingProduct.isPresent())
        {
            // Update the existing product with the new data
            Product productToUpdate = existingProduct.get();
            productToUpdate.setName(data.getName());
            productToUpdate.setDescription(data.getDescription());
            productToUpdate.setPrice(data.getPrice());

            // Save the updated product
            Product updatedProduct = productRepo.save(productToUpdate);
            return ResponseEntity.ok(updatedProduct);
        }
        else
        {
            // Product does not exist
            return ResponseEntity.notFound().build();
        }
    }

    //delete product
    public ResponseEntity<?> deleteProduct(Integer productId) {
        if (productRepo.findById(productId).isPresent())
        {
            //product exists
            productRepo.deleteById(productId);
            return ResponseEntity.ok().build();
        }
        else
        {
            //product does not exist
            return ResponseEntity.notFound().build();
        }
    }

    //get all products
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productRepo.findAll());
    }
}
