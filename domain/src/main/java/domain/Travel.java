package domain;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
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
        var total = BigDecimal.valueOf(0);
        for (var day : days(form)) {
            for (var entry : durationAllowances) {
                if (entry.getKey().test(day.duration())) {
                    total = total.add(entry.getValue());
                    break;
                }
            }
        }
        return total;
    }

    private Iterable<Day> days(TravelExpenseForm form) {
        List<Day> days = new ArrayList<>();
        var startOfDay = form.start();
        do {
            var endOfDay = startOfDay.plusDays(1).truncatedTo(ChronoUnit.DAYS);

            if (endOfDay.isAfter(form.end())) {
                endOfDay = form.end();
            }

            days.add(new Day(startOfDay, endOfDay));
            startOfDay = endOfDay;
        } while (startOfDay.isBefore(form.end()));
        return days;
    }

    record Day(LocalDateTime start, LocalDateTime end) {
        Duration duration() {
            return Duration.between(start, end);
        }
    }
}
