package domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

record TravelAllowance(
        LocalDateTime start,
        LocalDateTime end,
        String destination,
        String reason,
        BigDecimal allowance) {
}
