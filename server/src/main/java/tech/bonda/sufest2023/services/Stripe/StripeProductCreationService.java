package tech.bonda.sufest2023.services.Stripe;

import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bonda.sufest2023.models.DTOs.ProductDto;
import tech.bonda.sufest2023.repository.ProductRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class StripeProductCreationService {
    private static final String DEFAULT_IMAGE_URL = "https://yt3.ggpht.com/FYELqR3XE5tHW34BmJ4e9EjVkt7Aa3IKiHmhg2AEHudalaWtEPzLnaOfd8KzkGgPKbrH6ybhTg=s68-c-k-c0x00ffffff-no-rj";
    private static final String SUCCESS_URL = "https://youtube.com/";
    private static final String CANCEL_URL = "https://chat.openai.com/";
    private static final String CURRENCY = "bgn";

    private final ProductRepo productRepo;

    public String createProduct(ProductDto data) {
        try
        {
            Product product = createStripeProduct(data);
            return product.getId();
        } catch (StripeException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<?> getStripeUrl(List<Integer> dbId) {
        List<String> stripeIds = dbIdToStripePriceId(dbId);
        String url = createSession(stripeIds);
        return (url != null) ? ResponseEntity.ok(url) : ResponseEntity.badRequest().body("Error creating session");
    }

    private Product createStripeProduct(ProductDto data) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("name", data.getName());
        params.put("description", data.getDescription());
        params.put("images", new String[]{data.getImage() != null ? data.getImage() : DEFAULT_IMAGE_URL});
        Product product = Product.create(params);
        createPriceForProduct(product.getId(), data.getPrice());
        return product;
    }

    private void createPriceForProduct(String productId, Double price) throws StripeException {
        Map<String, Object> priceData = new HashMap<>();
        priceData.put("unit_amount", Math.round(price * 100));
        priceData.put("currency", CURRENCY);
        priceData.put("product", productId);
        Price.create(priceData);
    }

    private String createSession(List<String> productIds) {
        try
        {
            Map<String, Long> productCount = countIds(productIds);
            List<SessionCreateParams.LineItem> lineItems = createLineItems(productCount);

            SessionCreateParams.Builder builder = SessionCreateParams.builder()
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(SUCCESS_URL)
                    .setCancelUrl(CANCEL_URL);

            for (SessionCreateParams.LineItem lineItem : lineItems)
            {
                builder.addLineItem(lineItem);
            }

            SessionCreateParams params = builder.build();

            Session session = Session.create(params);
            System.out.println("Checkout session ID: " + session.getId());
            System.out.println("Checkout session URL: " + session.getUrl());
            return session.getUrl();
        } catch (StripeException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private List<SessionCreateParams.LineItem> createLineItems(Map<String, Long> productCount) {
        return productCount.entrySet().stream()
                .map(entry -> SessionCreateParams.LineItem.builder()
                        .setPrice(entry.getKey())
                        .setQuantity(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private Map<String, Long> countIds(List<String> ids) {
        Map<String, Long> nameCounts = new HashMap<>();

        for (String id : ids)
        {
            nameCounts.put(id, nameCounts.getOrDefault(id, 0L) + 1);
        }

        return nameCounts;
    }

    public List<String> dbIdToStripePriceId(List<Integer> dbIds) {
        List<String> stripePriceIds = new ArrayList<>();


        for (Integer dbId : dbIds) {
            Optional<tech.bonda.sufest2023.models.Product> productOptional = productRepo.findById(dbId);
            if (productOptional.isPresent()) {
                tech.bonda.sufest2023.models.Product product = productOptional.get();

                // Assuming each product has one associated price
                String productId = product.getStripeId();

                try {
                    String priceId = retrievePriceIdForProduct(productId);
                    stripePriceIds.add(priceId);
                } catch (StripeException e) {
                    e.printStackTrace();
                }
            }
        }

        return stripePriceIds;
    }

    private String retrievePriceIdForProduct(String productId) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("product", productId);
        Price price = Price.list(params).getData().get(0);
        System.out.println("Price ID: " + price.getId());
        return price.getId();
    }
}
