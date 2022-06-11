package domain;

import java.math.BigDecimal;
import java.util.Collection;

@FunctionalInterface
public interface AllowanceStrategy {
    BigDecimal calculate(Collection<Day> days);
}
