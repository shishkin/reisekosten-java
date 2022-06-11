package domain.allowances;

import domain.entities.Day;

import java.math.BigDecimal;
import java.util.Collection;

public class AllowanceOutsideEu implements AllowanceStrategy {
    @Override
    public BigDecimal calculate(Collection<Day> days) {
        return days.stream().reduce(
                BigDecimal.ZERO,
                (total, day) -> total.add(BigDecimal.valueOf(100)),
                BigDecimal::add);
    }
}
