package com.kifiya.PromoQuoter.exception;

import java.time.OffsetDateTime;
import java.util.List;

public record ErrorResponse (
        String message,
        String details,
        int errorCode,
        OffsetDateTime timestamp
) {}