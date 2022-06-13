package application.usecases.report;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public record TravelViewModel(
        ZonedDateTime start,
        ZonedDateTime end,
        String reason,
        String destination,
        BigDecimal allowance) {
}
