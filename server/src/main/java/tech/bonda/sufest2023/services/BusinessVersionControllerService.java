package tech.bonda.sufest2023.services;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bonda.sufest2023.models.AppProduct;
import tech.bonda.sufest2023.models.DTOs.ProductDto;
import tech.bonda.sufest2023.repository.CompanyRepo;
import tech.bonda.sufest2023.repository.ProductRepo;
import tech.bonda.sufest2023.services.Stripe.StripeProductCreationService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BusinessVersionControllerService {

    private final CompanyRepo companyRepo;

    private final ProductRepo productRepo;

    private final StripeProductCreationService stripeProductCreationService;


    public ResponseEntity<?> addProductToCompany(ProductDto data) {
        if (companyRepo.findById(data.getCompanyId()).isPresent())
        {
            if (productRepo.findByName(data.getName()).isPresent())
            {
                return ResponseEntity.badRequest().body("Product with this name already exists");
            }
            else
            {
                AppProduct appProduct = AppProduct.builder()
                        .name(data.getName())
                        .description(data.getDescription())
                        .price(data.getPrice())
                        .company(companyRepo.findById(data.getCompanyId()).get())
                        .image(data.getImage())
                        .build();

                String s = stripeProductCreationService.createProduct(data);
                appProduct.setStripeId(s);

                return ResponseEntity.ok(productRepo.save(appProduct));
            }
        }
        else
        {
            return ResponseEntity.badRequest().body("Company with this id does not exist");
        }
    }

    public ResponseEntity<?> getProduct(Integer productId) {
        if (productRepo.findById(productId).isPresent())
        {
            return ResponseEntity.ok(productRepo.findById(productId));
        }
        else
        {
            return ResponseEntity.badRequest().body("Product with the given id does not exist");
        }
    }

    public ResponseEntity<?> getAllProductsOfCompany(Integer companyId) {
        if (companyRepo.findById(companyId).isPresent())
        {
            return ResponseEntity.ok(productRepo.findAllByCompanyId(companyId));
        }
        else
        {
            return ResponseEntity.badRequest().body("Company with the given id does not exist");
        }
    }

    public ResponseEntity<?> deleteProduct(Integer productId) {
        if (productRepo.findById(productId).isPresent())
        {
            String stripeId = productRepo.findById(productId).get().getStripeId();
            try
            {
                Product product = Product.retrieve(stripeId);
                product.delete();
                productRepo.deleteById(productId);
                return ResponseEntity.ok().build();
            } catch (StripeException e)
            {
                e.printStackTrace();
                ResponseEntity.badRequest().body("Product could not be deleted");
            }
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
        return null;
    }

    public ResponseEntity<?> updateProduct(Integer productId, ProductDto data) {
        Optional<AppProduct> existingProduct = productRepo.findById(productId);
        if (existingProduct.isPresent())
        {
            AppProduct appProductToUpdate = updateAppProduct(data, existingProduct);

            String stripeId = productRepo.findById(productId).get().getStripeId();
            AppProduct updatedAppProduct = productRepo.save(appProductToUpdate);
            Long updatedPriceForStripe = Math.round(updatedAppProduct.getPrice() * 100);


            try
            {
                Product product = Product.retrieve(stripeId);

                Map<String, Object> params = new HashMap<>();
                params.put("name", updatedAppProduct.getName());
                params.put("description", updatedAppProduct.getDescription());
//                params.put("images", updatedAppProduct.getImage());

                Product updatedProduct = product.update(params);

                Map<String, Object> priceParams = new HashMap<>();
                priceParams.put("unit_amount", updatedPriceForStripe);
                priceParams.put("currency", "bgn");
                priceParams.put("product", stripeId);

                Price price = Price.create(priceParams);
                Product product2 = Product.retrieve(stripeId);

                Map<String, Object> productUpdateParams = new HashMap<>();
                productUpdateParams.put("default_price", price.getId());
                product2.update(productUpdateParams);

                System.out.println("Product updated successfully.");


            } catch (StripeException e)
            {
                e.printStackTrace();
            }
            return ResponseEntity.ok(updatedAppProduct);
        }
        else
        {
            return ResponseEntity.badRequest().body("Product with the given id does not exist");
        }
    }

    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productRepo.findAll());
    }

    private static AppProduct updateAppProduct(ProductDto data, Optional<AppProduct> existingProduct) {
        AppProduct appProductToUpdate = existingProduct.get();
        if (data.getName() != null) {
            appProductToUpdate.setName(data.getName());
        }
        if (data.getDescription() != null) {
            appProductToUpdate.setDescription(data.getDescription());
        }
        if (data.getPrice() != null) {
            appProductToUpdate.setPrice(data.getPrice());
        }
        if (data.getImage() != null) {
            appProductToUpdate.setImage(data.getImage());
        }
        return appProductToUpdate;
    }
}
