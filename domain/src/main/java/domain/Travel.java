package domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Predicate;

public record Travel(TravelExpenseForm form) {

    static final List<Entry<Predicate<Duration>, BigDecimal>> durationAllowances = List.of(
            new SimpleEntry<>(d -> d.compareTo(Duration.ofHours(24)) >= 0, BigDecimal.valueOf(24)),
            new SimpleEntry<>(d -> d.compareTo(Duration.ofHours(12)) >= 0, BigDecimal.valueOf(12)),
            new SimpleEntry<>(d -> d.compareTo(Duration.ofHours(8)) >= 0, BigDecimal.valueOf(6))
    );

    BigDecimal allowance() {
        for (var entry : durationAllowances) {
            if (entry.getKey().test(duration())) {
                return entry.getValue();
            }
        }
        return BigDecimal.valueOf(0);
    }

    Duration duration() {
        return Duration.between(form.start(), form.end());
    }
}
