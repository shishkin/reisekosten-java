package domain.allowances;

import domain.entities.Day;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class AllowanceInsideEu implements AllowanceStrategy {
    static final List<Map.Entry<Predicate<Duration>, BigDecimal>> durationAllowances = List.of(
            new AbstractMap.SimpleEntry<>(d -> d.compareTo(Duration.ofHours(24)) >= 0, BigDecimal.valueOf(24)),
            new AbstractMap.SimpleEntry<>(d -> d.compareTo(Duration.ofHours(12)) >= 0, BigDecimal.valueOf(12)),
            new AbstractMap.SimpleEntry<>(d -> d.compareTo(Duration.ofHours(8)) >= 0, BigDecimal.valueOf(6))
    );

    public BigDecimal calculate(Collection<Day> days) {
        return days.stream().reduce(
                BigDecimal.valueOf(0),
                (total, day) -> {
                    var allowance = durationAllowances
                            .stream()
                            .filter(e -> e.getKey().test(day.duration()))
                            .findFirst()
                            .map(Map.Entry::getValue)
                            .orElse(BigDecimal.valueOf(0));
                    return total.add(allowance);
                },
                BigDecimal::add);
    }
}
