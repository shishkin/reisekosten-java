package domain.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TravelAllowance(
        LocalDateTime start,
        LocalDateTime end,
        String destination,
        String reason,
        BigDecimal allowance) {
}
