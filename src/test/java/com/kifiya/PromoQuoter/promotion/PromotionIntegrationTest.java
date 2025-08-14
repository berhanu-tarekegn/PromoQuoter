package com.kifiya.PromoQuoter.promotion;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PromotionIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testCreateAndRetrievePromotion() throws Exception {
        String baseUrl = "http://localhost:" + port + "/promotions";

        BuyXGetYPromotion promotion = new BuyXGetYPromotion();
        promotion.setName("Buy 1 Get 1 Free");
        promotion.setActive(true);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<BuyXGetYPromotion> request = new HttpEntity<>(promotion, headers);

        ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.POST, request, String.class);
        assertEquals(200, response.getStatusCodeValue());

        BuyXGetYPromotion createdPromotion = objectMapper.readValue(response.getBody(), BuyXGetYPromotion.class);
        assertNotNull(createdPromotion);
        assertEquals(promotion.getName(), createdPromotion.getName());
    }
}
