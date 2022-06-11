package domain.allowances;

import domain.entities.Day;

import java.math.BigDecimal;
import java.util.Collection;

@FunctionalInterface
public interface AllowanceStrategy {
    BigDecimal calculate(Collection<Day> days);
}
