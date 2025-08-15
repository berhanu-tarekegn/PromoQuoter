package com.kifiya.PromoQuoter.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kifiya.PromoQuoter.common.AbstractEntity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String hashRequestObject(Object request) {
        try {
            String json = objectMapper.writeValueAsString(request);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(json.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash cart request", e);
        }
    }
}