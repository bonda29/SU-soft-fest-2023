package tech.bonda.sufest2023.services.Stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bonda.sufest2023.models.DTOs.ProductDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StripeProductCreationService {

    private static final String DEFAULT_IMAGE_URL = "https://yt3.ggpht.com/FYELqR3XE5tHW34BmJ4e9EjVkt7Aa3IKiHmhg2AEHudalaWtEPzLnaOfd8KzkGgPKbrH6ybhTg=s68-c-k-c0x00ffffff-no-rj";

    /**
     * Creates a product in Stripe.
     *
     * @param data Product data including name, description, price, and image URL.
     * @return ResponseEntity indicating the result.
     */
    public ResponseEntity<?> createProduct(ProductDto data) {
        try
        {
            Product product = createStripeProduct(data);
            System.out.println("Product ID: " + product.getId());
            return ResponseEntity.ok("Product created");
        } catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error creating product");
        }
    }

    private Product createStripeProduct(ProductDto data) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", data.getName());
        params.put("description", data.getDescription());
        params.put("images", new String[]{DEFAULT_IMAGE_URL});
        Product product = Product.create(params);
        createPriceForProduct(product.getId(), data.getPrice());
        return product;
    }

    private void createPriceForProduct(String productId, Double price) throws StripeException {
        Map<String, Object> priceData = new HashMap<>();
        priceData.put("unit_amount", price.longValue() * 100);
        priceData.put("currency", "bgn");
        priceData.put("product", productId);
        Price.create(priceData);
    }


    /**
     * Creates a checkout session in Stripe.
     *
     * @param productIds List of product IDs to add to the session.
     * @return ResponseEntity indicating the result.
     */
    public ResponseEntity<?> createSession(List<String> productIds) {
        try {
            Map<String, Long> productCount = countIds(productIds);
            List<SessionCreateParams.LineItem> lineItems = createLineItems(productCount);

            SessionCreateParams.Builder builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl("https://youtube.com/")
                    .setCancelUrl("https://chat.openai.com/");

            for (SessionCreateParams.LineItem lineItem : lineItems) {
                builder.addLineItem(lineItem);
            }

            SessionCreateParams params = builder.build();

            Session session = Session.create(params);
            System.out.println("Checkout session ID: " + session.getId());
            System.out.println("Checkout session Url: " + session.getUrl());
            return ResponseEntity.ok("Session created");
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error creating session");
        }
    }

    private List<SessionCreateParams.LineItem> createLineItems(Map<String, Long> productCount) {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();

        productCount.forEach((id, quantity) -> {
            SessionCreateParams.LineItem item = SessionCreateParams.LineItem.builder()
                    .setPrice(id)
                    .setQuantity(quantity)
                    .build();
            lineItems.add(item);
        });

        return lineItems;
    }

    private Map<String, Long> countIds(List<String> ids) {
        Map<String, Long> nameCounts = new HashMap<>();

        for (String name : ids) {
            long count = nameCounts.getOrDefault(name, 0L);
            nameCounts.put(name, count + 1);
        }

        return nameCounts;
    }


}
