package application.usecases.report;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TravelViewModel(
        LocalDateTime start,
        LocalDateTime end,
        String reason,
        String destination,
        BigDecimal allowance) {
}
