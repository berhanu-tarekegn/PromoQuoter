package com.kifiya.PromoQuoter.promotion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PromotionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreatePromotion() throws Exception {
        String promotionJson = "{\"name\":\"Buy 1 Get 1 Free\",\"promotion_type\":\"BUY_X_GET_Y\"}";

        mockMvc.perform(post("/promotions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(promotionJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buy 1 Get 1 Free"));
    }

    @Test
    void testGetAllPromotions() throws Exception {
        mockMvc.perform(get("/promotions"))
                .andExpect(status().isOk());
    }
}
