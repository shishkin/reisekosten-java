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
import java.util.stream.StreamSupport;

public record Travel(TravelExpenseForm form) {

    static final List<Entry<Predicate<Duration>, BigDecimal>> durationAllowances = List.of(
            new SimpleEntry<>(d -> d.compareTo(Duration.ofHours(24)) >= 0, BigDecimal.valueOf(24)),
            new SimpleEntry<>(d -> d.compareTo(Duration.ofHours(12)) >= 0, BigDecimal.valueOf(12)),
            new SimpleEntry<>(d -> d.compareTo(Duration.ofHours(8)) >= 0, BigDecimal.valueOf(6))
    );

    BigDecimal allowance() {
        return StreamSupport.stream(days(form).spliterator(), false)
                .reduce(
                        BigDecimal.valueOf(0),
                        (total, day) -> {
                            var allowance = durationAllowances
                                    .stream()
                                    .filter(e -> e.getKey().test(day.duration()))
                                    .findFirst()
                                    .map(Entry::getValue)
                                    .orElse(BigDecimal.valueOf(0));
                            return total.add(allowance);
                        },
                        BigDecimal::add);
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
