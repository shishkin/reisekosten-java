package domain;

import java.math.BigDecimal;
import java.util.Collection;

record Report(Collection<TravelAllowance> allowances) {

    BigDecimal totalAllowance() {
        return allowances
                .stream()
                .map(TravelAllowance::allowance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
