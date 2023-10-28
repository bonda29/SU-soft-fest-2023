package tech.bonda.sufest2023.services.Coinbase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import okhttp3.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bonda.sufest2023.repository.ProductRepo;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CoinbaseCheckoutService {

    private final ProductRepo productRepo;
    private final String API_KEY = "6ba7c873-628f-46d9-8f1e-e123e6534ef4";

    public ResponseEntity<?> createCheckout(List<Integer> data) throws IOException {
        double PRICE = 0;

        for (Integer integer : data) {
            PRICE += productRepo.findById(integer).get().getPrice();
        }



        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,"{\"name\":\"Payment\",\"pricing_type\":\"fixed_price\",\"description\":\"You were automatically redirected to Coinbase to pay: " + PRICE + " for the items you have selected \",\"local_price\":{\"amount\": " + PRICE + ",\"currency\":\"bgn\"},\"redirect_url\":\"https://www.youtube.com/\",\"cancel_url\":\"https://chat.openai.com/\"}");
        Request request = new Request.Builder()
                .url("https://api.commerce.coinbase.com/charges")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("X-CC-Api-Key", API_KEY)
                .build();
        Response response = client.newCall(request).execute();
        ResponseBody responseBody = response.body();
            if (responseBody != null) {
                String jsonResponse = responseBody.string();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                JsonNode dataNode = jsonNode.get("data");
                String url = String.valueOf(dataNode.get("hosted_url"));

                System.out.println("URL: " + url);

                ObjectNode objectNode = new ObjectMapper().createObjectNode();
                objectNode.put("url", url);
                return ResponseEntity.ok(objectNode);
            }
            else
            {
                return ResponseEntity.badRequest().body("Something went wrong");
            }
    }
}
