package domain;

import java.math.BigDecimal;
import java.time.Duration;

public record Travel(TravelExpenseForm form) {
    BigDecimal allowance() {
        if (duration().compareTo(Duration.ofHours(24)) >= 0) {
            return BigDecimal.valueOf(24);
        }
        if (duration().compareTo(Duration.ofHours(12)) >= 0) {
            return BigDecimal.valueOf(12);
        }
        if (duration().compareTo(Duration.ofHours(8)) >= 0) {
            return BigDecimal.valueOf(8);
        }
        return BigDecimal.valueOf(0);
    }

    Duration duration() {
        return Duration.between(form.start(), form.end());
    }
}
